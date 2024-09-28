<div align="center", style="display: flex; overflow-x: auto; white-space: nowrap;"><img src="https://github.com/RysanekRivera/Hiring/blob/master/app/src/main/res/drawable/hiring_icon.png" alt="Image 1" style="width: 100px; height: 110px;"></div>

# <p align="center">Hiring </p>

<br>
<br>

![Gif](https://github.com/RysanekRivera/Hiring/blob/master/app/src/main/res/drawable/workplace.gif)

<br>

__Hiring__ is a robust Android application that demonstrates best practices in modern Android development. It fetches data from the [Hiring API](https://fetch-hiring.s3.amazonaws.com/hiring.json), and displays a comprehensive list of employees. The app efficiently filters out entries without a name and presents the data sorted first by _listId_ and then by _name_. It features an intuitive and user-friendly interface, including:
- __Dynamic Search__: Quickly find employees using a responsive search bar that supports real-time filtering.
- __Flexible Layouts__: Seamlessly switch between different grid layouts to suit earch user's preference.
- __Smart Scrolling Behavior__: The search bar intelligently disappears when scrolling down and reappears when scrolling up, for better visibility and user experience.
- __State Management__: The app utilizes Jetpack Compose's state management capabilities to efficiently manage and react to UI state changes, reducing the risk of memory leaks and ensuring that UI updates are handled in a predictable manner.
- __Event Handling__: User interactions such as lauching of SnackBars, Alert Dialogs are managed independently through the ViewModel layer, ensuring that business logic is decoupled from the UI.
- __Reactive Asynchronous Programming__: The app leverages Kotlin Coroutines and StateFlow for efficient, non-blocking data fetching and UI updates, ensuring a smooth and responsive user experience. This approach enables the app to handle network latency and large data sets gracefully, while maintaining seamless UI performance.

The app is built using a modular and scalable architecture to ensure maintainability and testability. It leverages modern Android development tools and practices, including:

- __Jetpack Compose__: For building a declarative UI, making it easy to create and manage UI components.
- __Compose Navigation__: To manage app navigation, ensuring a clean separation between UI and navigation logic.
- __MVVM Pattern__: Separating business logic from UI to facilitate testing and maintainability.
- __Multi-Module Architecture__: For better separation of concerns, faster build times, and more flexible code management.
- __Clean Architecture__: Following Uncle Bob's principles, separating the application into multiple layers (data, domain, and presentation) to achieve a clean and maintainable codebase.
- __Dagger-Hilt__: For dependency injection, reducing boilerplate code and improving the testability of the app.

This project reflects industry standards for building scalable, maintainable, and testable Android applications. By adhering to modern development practices and utilizing a clean architecture, making it easily extensible and adaptable to future changes.

<br>

## Images

<div align="center", style="display: flex; overflow-x: auto; white-space: nowrap;">
    <img src="https://github.com/RysanekRivera/Hiring/blob/master/app/src/main/res/drawable/hiring_1.png" alt="Image 1" style="width: 45%; height: auto; margin-right: 10px">
    <img src="https://github.com/RysanekRivera/Hiring/blob/master/app/src/main/res/drawable/hiring_2.png" alt="Image 2" style="width: 45%; height: auto; border-radius: 10px;">
</div>
<br>
<div align="center", style="display: flex; overflow-x: auto; white-space: nowrap; gap: 10px;">
  <img src="https://github.com/RysanekRivera/Hiring/blob/master/app/src/main/res/drawable/hiring_4.png" alt="Image 3" style="width: 45%; height: auto; margin-right: 10px">
  <img src="https://github.com/RysanekRivera/Hiring/blob/master/app/src/main/res/drawable/hiring_5.png" alt="Image 4" style="width: 45%; height: auto;">
</div>
<br>
<div align="center", style="display: flex; overflow-x: auto; white-space: nowrap; gap: 10px;">
  <img src="https://github.com/RysanekRivera/Hiring/blob/master/app/src/main/res/drawable/hiring_7.png" alt="Image 5" style="width: 45%; height: auto; margin-right: 10px">
  <img src="https://github.com/RysanekRivera/Hiring/blob/master/app/src/main/res/drawable/hiring_8.png" alt="Image 6" style="width: 45%; height: auto; ">
</div>
<br>
<div align="center", style="display: flex; overflow-x: auto; white-space: nowrap; gap: 10px;">
  <img src="https://github.com/RysanekRivera/Hiring/blob/master/app/src/main/res/drawable/hiring_9.png" alt="Image 7" style="width: 45%; height: auto; margin-right: 10px">
  <img src="https://github.com/RysanekRivera/Hiring/blob/master/app/src/main/res/drawable/hiring_10.png" alt="Image 8" style="width: 45%; height: auto;">
</div>


