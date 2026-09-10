package com.projeto.egoodapp.views;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.egoodapp.R;
import com.projeto.egoodapp.chat.ChatAdapter;
import com.projeto.egoodapp.chat.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rvChat;
    private ChatAdapter adapter;
    private List<ChatMessage> messages;
    private EditText etMessage;
    private ImageButton btnSend;

    // Estados para o chatbot baseado em regras
    private enum State { 
        IDLE, 
        WAITING_PANELS, WAITING_VOLTAGE, WAITING_ENERGY_PRICE_SOLAR,
        WAITING_VEHICLE_COMBUSTION, WAITING_VEHICLE_ELECTRIC, 
        WAITING_GAS_PRICE, WAITING_ENERGY_PRICE,
        WAITING_CUSTOM_VEHICLE_NAME, WAITING_CUSTOM_CONSUMPTION
    }
    
    private State currentState = State.IDLE;
    private int numPanels = 0;
    private String selectedGasCar = "";
    private String selectedElectricCar = "";
    private double gasPrice = 0.0;
    private double energyPrice = 0.0;
    private double energyPriceSolar = 0.0;
    private double customConsumption = 11.0; // Padrão

    private final String[] COMBUSTION_VEHICLES = {
            "toyota corolla", "honda civic", "vw polo", "fiat argo", "chevrolet onix",
            "hyundai hb20", "jeep compass", "jeep renegade", "hyundai creta", "vw t-cross"
    };

    private final String[] ELECTRIC_VEHICLES = {
            "byd dolphin", "volvo ex30", "gwm ora 03", "kwid e-tech", "jac e-js1",
            "tesla model 3", "byd seal", "volvo xc40 recharge", "megane e-tech"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        rvChat = findViewById(R.id.rvChat);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        messages = new ArrayList<>();
        adapter = new ChatAdapter(messages);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setAdapter(adapter);

        // Mensagem inicial do bot
        addBotMessage("Olá! Sou o assistente e-good ⚡. Posso te ajudar com economia 'solar', comparação de 'veículos' ou do 'meu carro'. O que deseja?\n\n(Dica: digite 'voltar' a qualquer momento para a pergunta anterior)");

        btnSend.setOnClickListener(v -> {
            String text = etMessage.getText().toString().trim();
            if (!text.isEmpty()) {
                addUserMessage(text);
                etMessage.setText("");
                processRules(text);
            }
        });
    }

    private void addUserMessage(String text) {
        messages.add(new ChatMessage(text, ChatMessage.TYPE_USER));
        adapter.notifyItemInserted(messages.size() - 1);
        rvChat.scrollToPosition(messages.size() - 1);
    }

    private void addBotMessage(String text) {
        messages.add(new ChatMessage(text, ChatMessage.TYPE_BOT));
        adapter.notifyItemInserted(messages.size() - 1);
        rvChat.scrollToPosition(messages.size() - 1);
    }

    private void processRules(String userInput) {
        String input = userInput.toLowerCase();
        String response = "";

        // Comando para voltar à pergunta anterior
        if (input.equals("voltar")) {
            handleBackAction();
            return;
        }

        switch (currentState) {
            case WAITING_PANELS:
                try {
                    numPanels = Integer.parseInt(userInput.replaceAll("[^0-9]", ""));
                    response = "Ótimo! " + numPanels + " painéis. Qual o preço do kWh da sua energia (ex: 0.90)?";
                    currentState = State.WAITING_ENERGY_PRICE_SOLAR;
                } catch (Exception e) {
                    response = "Por favor, informe apenas o número de painéis.";
                }
                break;

            case WAITING_ENERGY_PRICE_SOLAR:
                try {
                    energyPriceSolar = Double.parseDouble(userInput.replace(",", "."));
                    response = "Entendido. Qual a voltagem do sistema? (110 ou 220)";
                    currentState = State.WAITING_VOLTAGE;
                } catch (Exception e) {
                    response = "Por favor, digite o valor do kWh (ex: 0.95).";
                }
                break;

            case WAITING_VOLTAGE:
                // Cálculo: Média de 50kWh por painel por mês * preço do kWh
                double economiaSolar = numPanels * 50.0 * energyPriceSolar;
                response = "Com " + numPanels + " painéis, você produz cerca de " + (numPanels * 50) + " kWh/mês.\n\n" +
                        "💰 Sua economia estimada é de R$ " + String.format("%.2f", economiaSolar) + " mensais ao carregar seu elétrico com energia própria! 🌱";
                currentState = State.IDLE;
                break;

            case WAITING_VEHICLE_COMBUSTION:
                boolean combustionFound = false;
                for (String v : COMBUSTION_VEHICLES) {
                    if (input.contains(v)) {
                        combustionFound = true;
                        selectedGasCar = v.toUpperCase();
                        break;
                    }
                }

                if (combustionFound) {
                    customConsumption = 11.0; // Reset para média padrão da lista
                    response = "Entendido! " + selectedGasCar + " selecionado. Agora escolha um modelo ELÉTRICO da lista:\n" +
                            "• BYD Dolphin\n• Volvo EX30\n• GWM Ora 03\n• Kwid E-Tech\n• JAC E-JS1\n• Tesla Model 3\n• BYD Seal\n• Volvo XC40 Recharge\n• Megane E-Tech";
                    currentState = State.WAITING_VEHICLE_ELECTRIC;
                } else {
                    response = "Esse veículo não está na nossa lista de combustão. Por favor, digite um nome válido da lista acima (ex: Honda Civic).";
                }
                break;

            case WAITING_CUSTOM_VEHICLE_NAME:
                selectedGasCar = userInput;
                response = "Legal! E qual o consumo médio desse carro (km/L)?";
                currentState = State.WAITING_CUSTOM_CONSUMPTION;
                break;

            case WAITING_CUSTOM_CONSUMPTION:
                try {
                    customConsumption = Double.parseDouble(userInput.replace(",", "."));
                    response = "Consumo registrado. Agora escolha um modelo ELÉTRICO da lista para comparar:\n" +
                            "• BYD Dolphin\n• Volvo EX30\n• GWM Ora 03\n• Kwid E-Tech\n• JAC E-JS1\n• Tesla Model 3\n• BYD Seal\n• Volvo XC40 Recharge\n• Megane E-Tech";
                    currentState = State.WAITING_VEHICLE_ELECTRIC;
                } catch (Exception e) {
                    response = "Por favor, digite apenas o número do consumo (ex: 12.5).";
                }
                break;

            case WAITING_VEHICLE_ELECTRIC:
                boolean electricFound = false;
                for (String v : ELECTRIC_VEHICLES) {
                    if (input.contains(v)) {
                        electricFound = true;
                        selectedElectricCar = v.toUpperCase();
                        break;
                    }
                }

                if (electricFound) {
                    response = "Boa escolha! " + selectedElectricCar + " selecionado. Qual o preço atual da GASOLINA por litro (ex: 5.80)?";
                    currentState = State.WAITING_GAS_PRICE;
                } else {
                    response = "Esse modelo elétrico não está na nossa lista. Por favor, escolha um modelo válido (ex: BYD Dolphin).";
                }
                break;

            case WAITING_GAS_PRICE:
                try {
                    gasPrice = Double.parseDouble(userInput.replace(",", "."));
                    response = "E qual o preço do kWh da ENERGIA na sua região (ex: 0.90)?";
                    currentState = State.WAITING_ENERGY_PRICE;
                } catch (Exception e) {
                    response = "Por favor, digite o valor da gasolina (ex: 6.00).";
                }
                break;

            case WAITING_ENERGY_PRICE:
                try {
                    energyPrice = Double.parseDouble(userInput.replace(",", "."));
                    double economia = calcularEconomia();
                    response = "Comparando o " + selectedGasCar + " com o " + selectedElectricCar + ":\n\n" +
                            "💰 Sua economia estimada é de R$ " + String.format("%.2f", economia) + " a cada 1.000km rodados!\n\n" +
                            "O futuro é elétrico! ⚡";
                    currentState = State.IDLE;
                } catch (Exception e) {
                    response = "Por favor, digite o valor do kWh (ex: 0.85).";
                }
                break;

            default:
                if (input.contains("solar")) {
                    response = "Vamos calcular! Quantos painéis solares você possui?";
                    currentState = State.WAITING_PANELS;
                } else if (input.contains("meu") && (input.contains("carro") || input.contains("veículo"))) {
                    response = "Vamos comparar seu carro! Qual o nome/modelo dele?";
                    currentState = State.WAITING_CUSTOM_VEHICLE_NAME;
                } else if (input.contains("veículo") || input.contains("veiculo") || input.contains("carro")) {
                    response = "Vamos comparar a economia! Primeiro, digite o nome de um carro a COMBUSTÃO da lista:\n" +
                            "• Toyota Corolla\n• Honda Civic\n• VW Polo\n• Fiat Argo\n• Chevrolet Onix\n• Hyundai HB20\n• Jeep Compass\n• Jeep Renegade\n• Hyundai Creta\n• VW T-Cross";
                    currentState = State.WAITING_VEHICLE_COMBUSTION;
                } else if (input.contains("oi") || input.contains("olá")) {
                    response = "Olá! Como posso ajudar? Podemos falar sobre 'solar', 'veículos' ou do 'meu carro'.";
                } else {
                    response = "Não entendi. Tente 'solar', 'veículos' ou 'meu carro'.";
                }
                break;
        }

        final String finalResponse = response;
        new Handler(Looper.getMainLooper()).postDelayed(() -> addBotMessage(finalResponse), 1000);
    }

    private void handleBackAction() {
        String backResponse = "";
        switch (currentState) {
            case WAITING_PANELS:
            case WAITING_VEHICLE_COMBUSTION:
            case WAITING_CUSTOM_VEHICLE_NAME:
                currentState = State.IDLE;
                backResponse = "Voltamos ao início. O que deseja? 'solar', 'veículos' ou 'meu carro'?";
                break;
            case WAITING_ENERGY_PRICE_SOLAR:
                currentState = State.WAITING_PANELS;
                backResponse = "Certo, voltando. Quantos painéis solares você possui?";
                break;
            case WAITING_VOLTAGE:
                currentState = State.WAITING_ENERGY_PRICE_SOLAR;
                backResponse = "Voltando. Qual o preço do kWh da sua energia (ex: 0.90)?";
                break;
            case WAITING_CUSTOM_CONSUMPTION:
                currentState = State.WAITING_CUSTOM_VEHICLE_NAME;
                backResponse = "Certo. Qual o nome/modelo do seu carro?";
                break;
            case WAITING_VEHICLE_ELECTRIC:
                currentState = State.IDLE; 
                backResponse = "Voltamos ao início. O que deseja? 'solar', 'veículos' ou 'meu carro'?";
                break;
            case WAITING_GAS_PRICE:
                currentState = State.WAITING_VEHICLE_ELECTRIC;
                backResponse = "Voltando. Escolha um modelo ELÉTRICO da lista para comparar.";
                break;
            case WAITING_ENERGY_PRICE:
                currentState = State.WAITING_GAS_PRICE;
                backResponse = "Voltando. Qual o preço atual da GASOLINA por litro (ex: 5.80)?";
                break;
            default:
                backResponse = "Estamos no menu inicial. O que deseja? 'solar', 'veículos' ou 'meu carro'?";
                break;
        }
        final String finalBackResponse = backResponse;
        new Handler(Looper.getMainLooper()).postDelayed(() -> addBotMessage(finalBackResponse), 500);
    }

    private double calcularEconomia() {
        // Usa o consumo customizado se informado, senão usa 11.0km/L como média
        double custoCombustao = (1000.0 / customConsumption) * gasPrice;
        double custoEletrico = (1000.0 / 6.5) * energyPrice; // Média 6.5km/kWh
        return custoCombustao - custoEletrico;
    }
}
