# SmolCam

SmolCam is a concept camera application that simulates manual camera controls in a clean, intuitive way. It's designed for those who appreciate the aesthetics of photography and enjoy having tactile control over their image settings.

## Screenshots

*Original Theme*


<img width="3200" height="1182" alt="image" src="https://github.com/user-attachments/assets/4f935801-fa20-4854-a09e-18218dfc1573" />


*Vibrant Theme*

<img width="3200" height="1185" alt="image" src="https://github.com/user-attachments/assets/462097cd-3b7e-44c1-899c-6c006660c85a" />


## Features

-   **Two Control Modes**: Seamlessly switch between Manual Mode and Filter Mode.
-   **Manual Camera Controls**: Adjust settings for the exposure triangle with interactive sliders:
    -   Aperture (F-stop)
    -   Shutter Speed (S)
    -   Exposure Value (EV)
    -   ISO
-   **Live Creative Filters**:
    -   **Saturation Slider**: Adjust the color intensity of your image in real-time.
    -   **Grain Overlay**: Add an atmospheric, film-like grain effect with adjustable intensity.
-   **Built-in Gallery**: View your captured moments in a beautiful, staggered grid gallery.
-   **Full-Screen Image Viewer**: Tap on any photo in the gallery to view it in a full-screen, zoomable interface with pinch-to-zoom functionality.
-   **Theme Customization**: Switch between "Original" and "Vibrant" themes to personalize the app's appearance.
-   **Elegant UI**: A clean, minimalist design with smooth animations and transitions.

## How to Build

This is a standard Android Studio project.

1.  Clone the repository.
2.  Open the project in Android Studio.
3.  Let Gradle sync the dependencies.
4.  Build and run the application on an Android device or emulator.

## Code Explanation

### 1. High-Level Project Overview

SmolCam is a mobile application for Android that simulates a camera interface with manual controls and image filters. It's designed to provide a tactile, retro-inspired user experience. The application has four main screens:

-   **Main Camera Screen (MainActivity)**: The primary interface where users can adjust camera settings, apply filters, and pretend to take pictures.
-   **Gallery Screen (GalleryActivity)**: A screen that displays a collection of pre-defined images in a staggered grid.
-   **Image Viewer Screen (ImageViewerActivity)**: A fullscreen viewer to see gallery images up close, with pinch-to-zoom functionality.
-   **Settings Screen (SettingsActivity)**: A screen that allows users to customize the app's theme and view credits.

The project is built using Kotlin, the modern, recommended language for Android development. It uses the standard Android SDK components and a few third-party libraries for enhanced functionality. The build system is Gradle.

### 2. Project Structure and Configuration Files

-   **`.gitignore`**
    This file tells the version control system (Git) which files and directories to ignore. It's crucial for keeping the repository clean by excluding generated files, user-specific settings, and build outputs. For instance, it ignores the `/build` directory, `local.properties` (which contains local SDK paths), and `.idea/` files (which store Android Studio's project settings).

-   **`build.gradle.kts` (Root Level)**
    This is the top-level build file for the entire project. Its primary job is to define the build configurations that are common to all modules within the project. In this case, it applies the Android Application and Kotlin Android plugins, but with `apply false`, indicating that these plugins will be applied within the individual modules (like the app module), not at the root level.

-   **`settings.gradle.kts`**
    This file is responsible for defining the project's module structure and setting up repository locations for dependencies.
    -   **`pluginManagement`**: Declares where Gradle should look for plugins (Google's Maven repository, Maven Central, and the Gradle Plugin Portal).
    -   **`dependencyResolutionManagement`**: Declares where Gradle should look for project dependencies. It specifies Google's repository, Maven Central, and JitPack (which is used for the PhotoView library).
    -   **`rootProject.name = "SmolCam"`**: Sets the name of the root project.
    -   **`include(":app")`**: Declares that the project includes a module named `app`.

-   **`gradle/libs.versions.toml`**
    This is a version catalog file, a modern way to manage dependencies and their versions in Gradle. It centralized all dependency information to ensure consistency and easier updates.
    -   **`[versions]`**: Defines version numbers as variables (e.g., `agp = "8.12.2"`).
    -   **`[libraries]`**: Declares the actual dependencies, linking a name (e.g., `androidx-core-ktx`) to its group, name, and version reference.
    -   **`[plugins]`**: Declares Gradle plugins in a similar way.

-   **`gradlew` & `gradlew.bat`**
    These are the Gradle Wrapper scripts for Unix-like systems (`gradlew`) and Windows (`gradlew.bat`) respectively. They allow developers to build the project without having to install a specific version of Gradle manually. The wrapper automatically downloads and uses the version specified in `gradle/wrapper/gradle-wrapper.properties`.

### 3. The `app` Module: Core of the Application

This is the main module containing all the application's code and resources.

-   **`app/build.gradle.kts`**
    This is the build script specifically for the `app` module.
    -   **Plugins**: It applies the `android.application` and `kotlin.android` plugins.
    -   **`android` block**:
        -   `namespace` and `applicationId`: Uniquely identifies the app.
        -   `compileSdk = 36`: Specifies the Android API level the app is compiled against.
        -   `defaultConfig`: Sets core configurations like `minSdk` (minimum Android version required), `targetSdk` (the version the app was tested against), and `versionCode`/`versionName`.
    -   **`buildTypes`**: Defines build variants. The `release` block specifies settings for the production version, such as enabling ProGuard for code shrinking and obfuscation.
    -   **`buildFeatures { viewBinding = true }`**: Enables View Binding, a feature that generates a binding class for each XML layout file. This allows for type-safe and null-safe access to views without using `findViewById`.
    -   **`dependencies` block**: Lists all the libraries the app depends on, using the aliases defined in the `libs.versions.toml` file. It includes standard AndroidX libraries, Google's Material Design library, testing libraries, and the third-party PhotoView library.

-   **`app/src/main/AndroidManifest.xml`**
    This is the app's manifest file. It's the control panel for the Android operating system, declaring the app's components and requirements.
    -   **`<application>`**: Defines application-level properties like the icon, label (app name), theme, and backup rules.
    -   **`<activity>`**: Declares each of the app's four activities. The `.MainActivity` has an `<intent-filter>` that designates it as the entry point of the application.

### 4. The User Interface: Layouts and Resources

The entire visual aspect of SmolCam is defined in the `res` (resources) directory.

-   **Layout Files (`app/src/main/res/layout`)**
    -   **`activity_main.xml`**: Defines the main camera screen using a `ConstraintLayout`, a `CardView` for the preview, and a `ViewAnimator` to switch between the manual and filter control panels.
    -   **`controls_manual.xml` & `controls_filter.xml`**: These are included by `activity_main.xml` and contain the sliders and buttons for adjusting camera settings.
    -   **`activity_gallery.xml`**: Defines the gallery screen, using a `RecyclerView` to display images in a staggered grid.
    -   **`item_gallery_image.xml`**: Defines the layout for a single item in the gallery, crucial for the shared element transition animation.
    -   **`activity_image_viewer.xml` & `item_fullscreen_image.xml`**: Defines the fullscreen viewer using a `ViewPager2` and the `PhotoView` library for zoom functionality.
    -   **`activity_settings.xml`**: Defines the settings screen, which includes options for changing the theme and viewing attribution information.

-   **Drawable Resources (`app/src/main/res/drawable`)**
    This directory contains all visual assets, including vector drawables for icons, shape drawables for backgrounds and buttons, and bitmap images (PNG/JPG) for camera backgrounds and overlays.

-   **Values Resources (`app/src/main/res/values`)**
    -   **`colors.xml`**: Defines the app's color palette for consistency.
    -   **`strings.xml`**: Contains all user-facing text strings for easy management and localization.
    -   **`styles.xml` & `themes.xml`**: Define the overall look and feel of the app, including base themes (for light and dark mode) and specific styles for individual UI components.
    -   **`attrs.xml`**: Defines custom attributes used in themes.

### 5. Application Logic: The Kotlin Code

This is where the app's behavior is implemented.

-   **`MainActivity.kt`**
    This is the most complex class, orchestrating the main camera screen. It manages the UI state, sets up click listeners for all buttons, handles the logic for both filter sliders (saturation, grain) and manual controls (Aperture, Shutter Speed, etc.), and implements the shutter animation effect.

-   **`InteractiveSliderView.kt` (Custom View)**
    This is a custom view built from scratch to provide the unique horizontal slider for manual controls. It uses Android's `Canvas` and `Paint` objects for all drawing and overrides `onTouchEvent()` to handle user gestures, including a smooth "snap-to-tick" animation using an `OverScroller`.

-   **`GalleryActivity.kt` & `GalleryAdapter.kt`**
    These two classes work together to display the gallery. `GalleryActivity` sets up the `RecyclerView` with a `StaggeredGridLayoutManager`. The `GalleryAdapter` binds the image data to the individual item layouts and handles click events, launching the `ImageViewerActivity` with a shared element transition for a smooth animation.

-   **`ImageViewerActivity.kt` & `FullscreenImageAdapter.kt`**
    These classes handle the fullscreen image viewing experience. The activity sets up a `ViewPager2` to allow swiping between images. The adapter populates the `ViewPager2` with `PhotoView` instances, which provides the pinch-to-zoom and pan functionality automatically.

-   **`SettingsActivity.kt`**
    This class manages the settings screen, allowing the user to switch between themes and access external links. It also handles the logic for recreating the activity when the theme is changed.

-   **`ThemeHelper.kt`**
    A helper object that manages theme-related logic, such as applying the selected theme to an activity and saving the user's preference to `SharedPreferences`.

## Credits

-   **Built by Navaneeth Sankar K P** (https://www.linkedin.com/in/navaneeth-sankar-k-p).
-   **Design Inspiration**: The user interface and overall aesthetic are inspired (initial brainstorming) by the beautiful design work of Hristo Hristov ([LinkedIn](https://www.linkedin.com/in/hristo-hristov-2bb0341a8)).
-   **Professor**: A huge thanks to my professor, **Sumi Alice Saji** ([LinkedIn](https://www.linkedin.com/in/sumi-alice-saji-0a073679/)), for being patient and really tolerant in helping me learn and making this possible.
-   **PhotoView**: This project uses the [PhotoView library](https://github.com/Baseflow/PhotoView) for the zoomable full-screen image viewer.
