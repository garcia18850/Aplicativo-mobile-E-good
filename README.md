# 🎯 e_goodApp

Aplicação Android para gestão e comércio de bens/produtos com integração Firebase, mapas e autenticação.

## 📋 Especificações do Projeto

### Plataforma
- **Framework**: Android Native (Kotlin)
- **API Mínima**: Android 8.0 (API 26)
- **API Alvo**: Android 12 (API 37)
- **Compilação**: API 37
- **Java**: Versão 21

### Stack Tecnológico
- **Linguagem**: Kotlin
- **Build System**: Gradle (Kotlin DSL)
- **Arquitetura**: MVVM (Model-View-ViewModel)
- **UI Framework**: Android Jetpack

### Principais Dependências
- **Firebase**: Authentication, Firestore, Cloud Storage
- **Google Services**: Maps API, Localização
- **Navegação**: Navigation Component
- **UI**: Material Design, RecyclerView, CardView
- **Carregamento de Imagens**: Glide
- **Lifecycle**: ViewModel, LiveData

### Estrutura do Projeto
```
e_goodApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/projeto/egoodapp/
│   │   │   └── res/
│   │   ├── test/
│   │   └── androidTest/
│   ├── build.gradle.kts
│   └── google-services.json (ignorado no git)
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## 🚀 Como Rodar o Projeto

### Pré-requisitos
- **Android Studio** (versão 2023.1.1 ou superior)
- **Java Development Kit (JDK)** versão 21 ou superior
- **SDK Platform Android 37** instalado no SDK Manager
- **Git** instalado e configurado

### 1️⃣ Clone o Repositório
```bash
git clone https://github.com/seu-usuario/e_goodApp.git
cd e_goodApp
```

### 2️⃣ Configurar Firebase
1. Baixe o arquivo `google-services.json` do Firebase Console
2. Coloque o arquivo em: `app/google-services.json`
3. **Não commite esse arquivo** (está no `.gitignore`)

### 3️⃣ Abrir no Android Studio
1. Abra **Android Studio**
2. Selecione **File** → **Open**
3. Navegue até a pasta `e_goodApp` e abra
4. Deixe o Gradle sincronizar automaticamente

### 4️⃣ Configurar Emulador/Dispositivo
**Opção A: Usar Emulador**
- Abra **Device Manager** no Android Studio
- Crie um novo emulador (Android 12 ou superior recomendado)
- Inicie o emulador

**Opção B: Usar Dispositivo Físico**
- Conecte seu telefone Android via USB
- Ative o **Modo de Desenvolvedor** nas Configurações
- Ative a **Depuração USB**

### 5️⃣ Compilar e Executar
```bash
# Via Android Studio
1. Clique em "Run" (Shift + F10)
2. Selecione o emulador ou dispositivo
3. Aguarde a compilação e instalação

# Via linha de comando (Gradle)
./gradlew assembleDebug        # Compilar APK de debug
./gradlew connectedAndroidTest # Rodar testes instrumentados
```

### 6️⃣ Verificar Instalação
- Procure por **e_goodApp** na lista de aplicativos do emulador/dispositivo
- Clique para abrir

## 🔧 Configurações Importantes

### gradle.properties
Certifique-se de que `local.properties` contém:
```properties
sdk.dir=/caminho/para/seu/Android/SDK
```

### AndroidManifest.xml Necessário
Permissões necessárias que devem estar declaradas:
- `android.permission.INTERNET` (Firebase)
- `android.permission.ACCESS_FINE_LOCATION` (Maps/Localização)
- `android.permission.ACCESS_COARSE_LOCATION`
- `android.permission.WRITE_EXTERNAL_STORAGE`
- `android.permission.READ_EXTERNAL_STORAGE`

## 🔐 Segurança

### Arquivos Sensíveis (Ignorados no Git)
- `google-services.json` - Credenciais Firebase
- `*.keystore` - Certificados de assinatura
- `*.jks` - Keystores
- `.env` - Variáveis de ambiente

## 📱 Build Variants
```bash
# Debug (desenvolvimento)
./gradlew assembleDebug

# Release (produção)
./gradlew assembleRelease
```

## 🧪 Testes
```bash
# Testes unitários
./gradlew test

# Testes instrumentados (em dispositivo/emulador)
./gradlew connectedAndroidTest
```

## 📦 Dependências do Gradle

Todas as dependências são gerenciadas via **Version Catalogs** (`libs` do `gradle/libs.versions.toml`):
- Android Core (appcompat, constraintlayout, material)
- Firebase BoM (gerencia versões automaticamente)
- Navigation Component
- Glide para carregamento de imagens
- Google Play Services
- Lifecycle (ViewModel, LiveData)

## 🐛 Troubleshooting

### Erro: "Gradle sync failed"
- Verifique se JDK 21 está configurado em **File** → **Project Structure** → **SDK Location**
- Limpe o cache: `./gradlew clean`

### Erro: "google-services.json not found"
- Baixe o arquivo do Firebase Console
- Coloque em `app/google-services.json`

### Emulador não aparece
- Verifique em **Device Manager** se o emulador está iniciado
- Tente: `adb devices`

### BuildTools ou compileSdk não encontrado
- Abra **SDK Manager** e instale:
  - Android SDK 37
  - Build-Tools 37.0.0 ou superior

## 📝 Notas de Desenvolvimento

- O projeto usa **View Binding** para melhor segurança de tipos
- Arquitetura **MVVM** com ViewModel e LiveData
- Firebase Realtime Database integrado
- Google Maps com localização em tempo real

## 👩‍💻 Autores

- Fernanda Falcão Kedouk Simões
- Giovanni Pereira Valente
- Kauã Garcia Francisco
- Nicole Kerne
- Maria Eduarda Souza Santos


## 📄 Licença

Este projeto foi feito para fins de estudo

---

**Dúvidas?** Consulte a [documentação oficial do Android](https://developer.android.com/) ou a [documentação do Firebase](https://firebase.google.com/docs/android/setup).
