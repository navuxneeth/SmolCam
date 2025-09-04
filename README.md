**SmolCam**
A simple, elegant little camera app preview for Android with a focus on creative control and a beautiful user interface. Design style inspired (initial design inspiration) from https://www.linkedin.com/in/hristo-hristov-2bb0341a8.

**About**
SmolCam is a concept camera application that simulates manual camera controls in a clean, intuitive way. It's designed for those who appreciate the aesthetics of photography and enjoy having tactile control over their image settings. The design style is inspired by the work of Hristo.

**How to Build**
This is a standard Android Studio project.

1. Clone the repository
2. Open the project in Android Studio.
3. Let Gradle sync the dependencies.
4. Build and run the application on an Android device or emulator.

**Credits**
Design Inspiration: The user interface and overall aesthetic are inspired (initial brainstorming) by the beautiful design work of Hristo Hristov (https://www.linkedin.com/in/hristo-hristov-2bb0341a8).
PhotoView: This project uses the PhotoView library for the zoomable full-screen image viewer (https://github.com/Baseflow/PhotoView).

**Features**
	Two Control Modes: Seamlessly switch between Manual Mode and Filter Mode.

  Manual Camera Controls: Adjust settings for the exposure triangle with interactive sliders:

		Aperture (F-stop)
	
		Shutter Speed (S)
	
		Exposure Value (EV)
	
		ISO

**Live Creative Filters:**

	Saturation Slider: Adjust the color intensity of your image in real-time.
	
	Grain Overlay: Add an atmospheric, film-like grain effect with adjustable intensity.

Built-in Gallery: View your captured moments in a beautiful, staggered grid gallery.

Full-Screen Image Viewer: Tap on any photo in the gallery to view it in a full-screen, zoomable interface.

Elegant UI: A clean, minimalist design with smooth animations and transitions.

**Screenshots**

(https://github.com/user-attachments/assets/eb64c445-5946-4fb3-a1d7-a3bd03dc16d6)

(https://github.com/user-attachments/assets/43b8543a-54ad-4ce8-8aef-f42475ccfb64)

(https://github.com/user-attachments/assets/c1558d1c-b9e0-4314-810b-1cc2ca28ab39)

(https://github.com/user-attachments/assets/0e62b065-59a0-4b60-b9ce-c9e2f8aa7434)

(https://github.com/user-attachments/assets/9ff5c49c-ce61-4538-98a7-61c71a31f3e8)

(https://github.com/user-attachments/assets/74e17ba6-3e00-4bc6-bd85-83e194993dad)

(https://github.com/user-attachments/assets/bd21cd02-2c55-435c-87c1-7be5086da14f)

(https://github.com/user-attachments/assets/9ef7373b-97bd-4553-b2a7-84ae1738fd25)


**How it works:**
{
1. High-Level Project Overview
SmolCam is a mobile application for Android that simulates a camera interface with manual controls and image filters. It's designed to provide a tactile, retro-inspired user experience. The application has three main screens:

Main Camera Screen (MainActivity): The primary interface where users can adjust camera settings, apply filters, and pretend to take pictures.

Gallery Screen (GalleryActivity): A screen that displays a collection of pre-defined images in a staggered grid.

Image Viewer Screen (ImageViewerActivity): A fullscreen viewer to see gallery images up close, with pinch-to-zoom functionality.

The project is built using Kotlin, the modern, recommended language for Android development. It uses the standard Android SDK components and a few third-party libraries for enhanced functionality. The build system is Gradle.

2. Project Structure and Configuration Files
The files you provided represent a standard Android Studio project. Let's look at the key configuration files at the root level.

.gitignore
This file tells the version control system (Git) which files and directories to ignore. It's crucial for keeping the repository clean by excluding generated files, user-specific settings, and build outputs. For instance, it ignores the /build directory, local.properties (which contains local SDK paths), and .idea/ files (which store Android Studio's project settings).

build.gradle.kts (Root Level)
This is the top-level build file for the entire project. Its primary job is to define the build configurations that are common to all modules within the project. In this case, it applies the Android Application and Kotlin Android plugins, but with apply false, indicating that these plugins will be applied within the individual modules (like the app module), not at the root level.

settings.gradle.kts
This file is responsible for defining the project's module structure and setting up repository locations for dependencies.

pluginManagement: Declares where Gradle should look for plugins (Google's Maven repository, Maven Central, and the Gradle Plugin Portal).

dependencyResolutionManagement: Declares where Gradle should look for project dependencies. It specifies Google's repository, Maven Central, and JitPack (which is used for the PhotoView library).

rootProject.name = "SmolCam": Sets the name of the root project.

include(":app"): Declares that the project includes a module named app.

gradle/libs.versions.toml
This is a version catalog file, a modern way to manage dependencies and their versions in Gradle. It centralized all dependency information to ensure consistency and easier updates.

[versions]: Defines version numbers as variables (e.g., agp = "8.12.2").

[libraries]: Declares the actual dependencies, linking a name (e.g., androidx-core-ktx) to its group, name, and version reference.

[plugins]: Declares Gradle plugins in a similar way.

gradlew & gradlew.bat
These are the Gradle Wrapper scripts for Unix-like systems (gradlew) and Windows (gradlew.bat) respectively. They allow developers to build the project without having to install a specific version of Gradle manually. The wrapper automatically downloads and uses the version specified in gradle/wrapper/gradle-wrapper.properties.

3. The app Module: Core of the Application
This is the main module containing all the application's code and resources.

app/build.gradle.kts
This is the build script specifically for the app module.

Plugins: It applies the android.application and kotlin.android plugins.

android block:

namespace and applicationId: Uniquely identifies the app.

compileSdk = 36: Specifies the Android API level the app is compiled against.

defaultConfig: Sets core configurations like minSdk (minimum Android version required), targetSdk (the version the app was tested against), and versionCode/versionName.

buildTypes: Defines build variants. The release block specifies settings for the production version, such as enabling ProGuard for code shrinking and obfuscation.

buildFeatures { viewBinding = true }: Enables View Binding, a feature that generates a binding class for each XML layout file. This allows for type-safe and null-safe access to views without using findViewById.

dependencies block:

Lists all the libraries the app depends on, using the aliases defined in the libs.versions.toml file.

It includes standard AndroidX libraries (core-ktx, appcompat, constraintlayout, activity), Google's Material Design library, testing libraries (junit, espresso), and the third-party PhotoView library for zoomable ImageViews.

app/src/main/AndroidManifest.xml
This is the app's manifest file. It's the control panel for the Android operating system, declaring the app's components and requirements.

<application>: Defines application-level properties like the icon, label (app name), theme, and backup rules.

<activity>: Declares each of the app's three activities:

.MainActivity: It has an <intent-filter> with action.MAIN and category.LAUNCHER, which designates it as the entry point of the application—the screen that opens when the user taps the app icon.

.GalleryActivity: Registered as a standard activity.

.ImageViewerActivity: Registered with a special Fullscreen theme to allow it to draw over the status and navigation bars.

4. The User Interface: Layouts and Resources
The entire visual aspect of SmolCam is defined in the res (resources) directory.

Layout Files (app/src/main/res/layout)
activity_main.xml: Defines the main camera screen.

It uses a ConstraintLayout for a flexible and flat view hierarchy.

A CardView holds the camera preview, which includes an ImageView for the background, another for the grain overlay, and a View for the gridlines.

A ViewAnimator (@+id/controls_animator) is used to switch between the two control panels: manual controls (controls_manual.xml) and filter controls (controls_filter.xml).

The bottom action bar is a LinearLayout containing ImageButtons for toggling controls and swapping backgrounds, and a FrameLayout for the custom shutter button.

controls_manual.xml & controls_filter.xml: These are included by activity_main.xml.

controls_manual.xml contains the custom InteractiveSliderView and a LinearLayout (@+id/setting_pills_container) where the setting buttons (F, S, EV, ISO) are dynamically added from the code.

controls_filter.xml contains two SeekBars, one for saturation and one for grain, each paired with an icon.

activity_gallery.xml: Defines the gallery screen.

It uses a CoordinatorLayout for rich interactions.

A RecyclerView (@+id/gallery_recycler_view) is the main element, which will be populated with the gallery items.

A FloatingActionButton (@+id/fab_camera) provides a prominent button to return to the camera screen.

item_gallery_image.xml: Defines the layout for a single item in the gallery RecyclerView.

It consists of two CardViews: one for the image itself and another for the title and date text below it, creating a clean, modern look. The ImageView has a transitionName set to "image_transition", which is key for the shared element transition animation when opening the image viewer.

activity_image_viewer.xml & item_fullscreen_image.xml:

The activity layout contains a ViewPager2 to allow swiping between images and a back button.

The item layout contains a com.github.chrisbanes.photoview.PhotoView, which is a special ImageView from an external library that provides out-of-the-box support for pinch-to-zoom and pan gestures.

Drawable Resources (app/src/main/res/drawable)
This directory contains all the visual assets that are not layouts or icons.

Vector Drawables (XML files): These are resolution-independent graphics. Examples include arrowbutton.xml, gridbutton.xml, filterbutton.xml, etc. They are defined using vector paths and can be scaled without losing quality.

Shape Drawables (XML files): These define simple geometric shapes, colors, and gradients. rounded_background.xml, for example, defines a rectangle with a solid color and rounded corners, used for the bottom control bar.

Bitmap Images (PNG/JPG files): These are the raster images used as backgrounds (background2.jpg, background3.jpg, etc.) and overlays (grain_background.png).

Values Resources (app/src/main/res/values)
colors.xml: Defines the app's color palette. Using named colors (screen_background, dark_teal, accent_red) makes it easy to maintain a consistent color scheme throughout the app.

strings.xml: Contains all the user-facing text strings, like the app's name (app_name). Centralizing strings is crucial for supporting multiple languages (internationalization).

styles.xml & themes.xml:

themes.xml defines the overall look and feel of the app. It sets the base theme (Theme.Material3.DayNight.NoActionBar), status bar color, and other window properties. There's also a values-night version for a dark mode theme.

styles.xml defines specific styles for individual views. For example, DefaultPillButton and SelectedPillButton define the appearance of the manual control buttons, allowing for easy reuse and consistency.

5. Application Logic: The Kotlin Code
This is where the app's behavior is implemented.

MainActivity.kt
This is the most complex class, orchestrating the main camera screen.

State Management: It holds variables to track the current state, such as isGridVisible, isManualMode, the currentBackgroundIndex, and the selectedSetting.

onCreate(): This is the entry point of the activity. It inflates the layout using View Binding and calls several setup methods.

setupClickListeners(): This method sets up OnClickListeners for all the buttons. For example, clicking gridButton toggles the visibility of the grid view. Clicking toggleControlsButton changes the displayedChild of the ViewAnimator to switch between manual and filter controls.

setupFilterSliders(): It finds the SeekBars for saturation and grain and attaches listeners to them. When a slider's progress changes, it calls a corresponding function (updateSaturation or sets the alpha on the grain overlay) to apply the visual effect in real-time.

Manual Controls Logic:

The settingValues map stores the current value for each camera setting (F, S, EV, ISO).

setupManualControls() sets up the custom InteractiveSliderView. It assigns a callback (onValueChanged) that updates the settingValues map and refreshes the UI whenever the slider's value changes.

updateManualControlsUI() and updatePillButtons() work together to dynamically create and update the pill-shaped buttons for F, S, EV, and ISO. When a setting is selected, the InteractiveSliderView is updated with the correct tick marks (ticksData), and the buttons are redrawn to reflect the current selection and values.

Shutter Animation: The shutterButton has both an OnClickListener and an OnTouchListener. The touch listener provides visual feedback by scaling the button down on press and back up on release. The click listener triggers playCaptureAnimation(), which uses an ObjectAnimator to create a quick white flash effect on the flash_overlay view, simulating a camera flash.

InteractiveSliderView.kt (Custom View)
This is a completely custom view built from scratch to provide the unique horizontal slider for manual controls.

Painting and Drawing: It uses Android's Canvas and Paint objects to draw everything. It has separate Paint objects for text, major ticks, minor ticks, the center line, and the dotted line, allowing for full control over the appearance.

onDraw(): This is the core drawing method. It calculates the position of each tick based on the current scroll offset (scrollXOffset) and draws the ticks and text onto the canvas.

Touch Handling & Scrolling:

onTouchEvent() captures user gestures. On ACTION_MOVE, it updates scrollXOffset based on how far the user has dragged their finger.

On ACTION_UP, it doesn't just stop; it calculates the nearest valid tick and uses an OverScroller to animate a smooth "snap" to that position. The scroller.startScroll() method initiates this animation.

computeScroll() is called by the system during the animation, allowing the view to update its scrollXOffset and redraw itself frame by frame, creating the smooth scrolling effect.

Callback: The onValueChanged function is a lambda that the MainActivity provides. The slider calls this function whenever its centered value changes, allowing it to communicate back to the activity without being tightly coupled to it.

GalleryActivity.kt & GalleryAdapter.kt
These two classes work together to display the gallery.

GalleryActivity.kt:

It defines the data for the gallery: a list of drawable resources for the images, and corresponding lists for titles and dates.

In onCreate(), it sets up the RecyclerView with a StaggeredGridLayoutManager, which allows for items of different heights to be arranged in a Pinterest-style grid.

It creates an instance of GalleryAdapter, passing the data to it, and attaches the adapter to the RecyclerView.

GalleryAdapter.kt:

This adapter is the bridge between the data and the RecyclerView.

onCreateViewHolder: Inflates the item_gallery_image.xml layout for each new item.

onBindViewHolder: Binds the data (image, title, date) to the views within a specific item at a given position.

Click Handling: It sets an OnClickListener on each item. When an item is clicked, it creates an Intent to start ImageViewerActivity. Crucially, it passes the list of all images and the position of the clicked image. It then uses ActivityOptionsCompat.makeSceneTransitionAnimation to create a beautiful shared element transition. This tells the Android system that the ImageView in the gallery item and the ImageView in the viewer activity are the "same" element, and it automatically animates the transition between the two screens.

ImageViewerActivity.kt & FullscreenImageAdapter.kt
These classes handle the fullscreen image viewing experience.

ImageViewerActivity.kt:

In onCreate(), it retrieves the list of images and the starting position from the Intent extras sent by the GalleryAdapter.

It sets up the ViewPager2 with a FullscreenImageAdapter and uses setCurrentItem to show the image that the user originally clicked on.

FullscreenImageAdapter.kt:

A simple adapter for the ViewPager2.

For each item, it inflates the item_fullscreen_image.xml layout and sets the image resource on the PhotoView. The PhotoView library then automatically handles all the complex logic for zooming and panning the image.
}

Thank you :)

