# Dynamic Swing Form Framework

A lightweight, annotation-driven dynamic form framework for **Java 11 +
Swing**.

This library automatically generates Swing forms from annotated model
classes.\
It supports reflection-based UI generation, validation,
internationalization (i18n), editable/read-only modes, and pluggable
component providers.

------------------------------------------------------------------------

# 🚀 Features

-   Annotation-based form generation
-   Automatic Swing component mapping
-   Primitive, Wrapper, and Enum support
-   Required and numeric validation
-   Multi-language (i18n) support for labels and validation messages
-   Editable / Read-only modes
-   Runtime editable toggle
-   Reset functionality
-   Pluggable component provider architecture
-   SOLID-compliant clean architecture
-   Java 11 compatible
-   Dependency-free

------------------------------------------------------------------------

# 📦 Installation

Include the source files in your Java 11 project.

No external dependencies required.

------------------------------------------------------------------------

# 🏗 Quick Start

## 1️⃣ Define Your Model

``` java
public class User {

    @Row(key = "user.name", row = 0, col = 0, required = true)
    private String name;

    @Row(key = "user.age", row = 0, col = 1, required = true)
    private int age;

    @Row(key = "user.active", row = 1, col = 0)
    private boolean active;

    @Row(key = "user.status", row = 1, col = 1)
    private Status status;
}
```

------------------------------------------------------------------------

## 2️⃣ Add Language File

messages_tr.properties

    user.name=Ad
    user.age=Yaş
    user.active=Aktif
    user.status=Durum

    validation.required={field} alanı zorunludur
    validation.number.invalid={field} sayısal olmalıdır

------------------------------------------------------------------------

## 3️⃣ Configure Language Provider

``` java
LanguageProvider provider =
    new ResourceBundleLanguageProvider(
        "messages",
        new Locale("tr")
    );

FormPanelFactory.getInstance()
        .setLanguageProvider(provider);
```

------------------------------------------------------------------------

## 4️⃣ Create the Form

``` java
DynamicForm<User> form =
    FormPanelFactory.getInstance()
        .createDialog(User.class, FormMode.EDIT);

frame.add(form.getPanel());
```

------------------------------------------------------------------------

## 5️⃣ Validate and Retrieve Data

``` java
ValidationResult result = form.validate();

if (!result.isValid()) {

    JOptionPane.showMessageDialog(
        frame,
        result.getCombinedMessage(),
        "Validation Error",
        JOptionPane.ERROR_MESSAGE
    );

    return;
}

User user = form.getData();
```

------------------------------------------------------------------------

# 🧩 Supported Field Types

  Java Type           Swing Component
  ------------------- -----------------
  String              JTextField
  int / Integer       JTextField
  double / Double     JTextField
  long / Long         JTextField
  float / Float       JTextField
  short / Short       JTextField
  byte / Byte         JTextField
  boolean / Boolean   JCheckBox
  Enum                JComboBox

------------------------------------------------------------------------

# 🌍 Internationalization (i18n)

All UI labels and validation messages are resolved via:

    LanguageProvider

You can implement:

-   ResourceBundle-based provider
-   Database-backed translations
-   REST-based translation service
-   Any custom solution

------------------------------------------------------------------------

# 🔁 Editable & Read-Only Mode

Initialize:

``` java
FormMode.EDIT
FormMode.READ_ONLY
```

Switch dynamically:

``` java
form.setEditable(false);
form.setEditable(true);
```

If initialized as READ_ONLY, it cannot be switched back to editable.

------------------------------------------------------------------------

# 🔄 Reset Form

``` java
form.reset();
```

Clears all input fields and validation state.

------------------------------------------------------------------------

# 🧠 Validation System

Features:

-   Required field validation
-   Numeric format validation
-   Multi-error aggregation
-   Field-based error tracking
-   Localized validation messages

Errors are stored as:

    Map<String, String> // key -> message

------------------------------------------------------------------------

# 🏛 Architecture Overview

Core Components:

-   DynamicForm`<T>`{=html} → Public API
-   DynamicFormImpl`<T>`{=html} → Core implementation
-   FormPanelFactory → Singleton factory
-   FieldMeta → Reflection metadata
-   FieldComponentProvider → Pluggable UI mapping
-   LanguageProvider → i18n abstraction
-   ValidationMessageResolver → Localized validation engine
-   ValidationResult → Error aggregation

Design Principles:

-   Single Responsibility Principle
-   Open/Closed Principle
-   UI and Validation separation
-   Reflection metadata caching
-   Extensible provider architecture

------------------------------------------------------------------------

# 🔌 Extending the Framework

To support new component types:

1.  Implement FieldComponentProvider
2.  Register it in FieldComponentRegistry

Example extensions:

-   DatePicker
-   Masked input
-   Money field
-   File chooser
-   Autocomplete dropdown

------------------------------------------------------------------------

# 🧪 Java Version

Compatible with:

    Java 11+

------------------------------------------------------------------------

# 🎯 Design Goals

-   Lightweight
-   Clean architecture
-   Extensible
-   Swing-focused
-   Production-ready
-   Framework-style modular design

------------------------------------------------------------------------

# 📌 Future Roadmap

-   @Min / @Max annotations
-   Pattern validation
-   Field-level error highlighting
-   Runtime locale switching
-   Layout strategy abstraction
-   Bean Validation compatibility

------------------------------------------------------------------------

# 👤 Author

Fatih OKSUZOGLU
