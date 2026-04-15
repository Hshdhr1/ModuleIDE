# ModuleIDE (decompiled Android sources)

> Репозиторий содержит декомпилированные Java-исходники и ресурсы Android-приложения  
> (папки `sources/` и `resources/`), а не готовый Android Studio проект.

## Что внутри

- `sources/` — декомпилированные Java-классы.
- `resources/` — Android-ресурсы, манифест и `.dex` файлы.
- `.gitignore` — служебные исключения Git.

## Что исправлено в этой версии

- Исправлен потенциальный `NullPointerException` в проверке сети (`sources/d.java`):
  - добавлена проверка `context == null`;
  - добавлена проверка `ConnectivityManager == null`;
  - используется `Context.CONNECTIVITY_SERVICE` вместо строкового литерала.

## Быстрый старт

### 1) Просмотр/анализ кода

Если нужно просто изучать проект:

```bash
git clone <repo-url>
cd ModuleIDE
```

Откройте папку в IDE (IntelliJ IDEA / Android Studio) как обычный каталог.

### 2) Сборка APK из декомпилированного набора (рекомендуемый путь)

Так как это не полноценный Gradle-проект, обычно пересборка делается инструментами реверс-инжиниринга:

1. Установите `apktool` и `apksigner` (из Android Build Tools).
2. Соберите apk из каталога с ресурсами/смали (если есть смали-структура).

Пример команд:

```bash
apktool b <decoded_apk_dir> -o app-unsigned.apk
apksigner sign --ks <keystore.jks> app-unsigned.apk
```

> Важно: в текущем репозитории хранятся `dex` и декомпилированные `java`, поэтому для корректной пересборки может потребоваться исходная структура decoded APK (особенно `smali/`).

### 3) Сборка Java-кода для статической проверки (опционально)

Можно запустить быструю проверку отдельных классов через `javac`, но для полной компиляции Android-классов потребуются Android SDK и `android.jar`.

Пример:

```bash
# путь к android.jar зависит от версии SDK
javac -cp $ANDROID_SDK_ROOT/platforms/android-34/android.jar sources/d.java
```

## Требования

- Java 11+ (рекомендуется 17)
- Android SDK (для `android.jar`, `apksigner`)
- apktool (если планируется обратная сборка APK)

## Полезные заметки

- Декомпилированные проекты часто содержат классы с неполным/искажённым кодом.
- Перед production-сборкой лучше переносить логику в чистый Android Gradle-проект и постепенно заменять декомпилированные участки.
