# PasswordGenerator

Пример приложения. Ключевые особенности:
- Приложение следует принципам android recommended architecture, полностью соответствует паттерну MVVM;
- Для хранения списков паролей используется Room и LiveData, тем самым обеспечивается соблюдение принципа single source of truth;
- Для навигации между экранами используется Android Navigation;
- Для экспорта/импорта используется ContentProvider, приложение, естественно, не требует никаких разрешений;
- Для хранения настроек используется SharedPreferences;
- Приложение имеет поддержку темной темы;
- Строковые данные хранятся в отведенном для них ресурсе, имеются английская и русская локали.

Подробнее с особенностями приложения можно ознакомиться в коде, там находятся подробные комментарии. Видео с демонстрацией работы приложения ниже.

https://github.com/xorec/PasswordGenerator/assets/143298866/a241514b-7deb-46d9-b5b4-6b0553a06086

