# Get Weather Forecast Android App
Get daily forecast 7 days of a city

![processed_1](https://user-images.githubusercontent.com/7643183/119255897-49ce5d80-bbe8-11eb-855c-4c4e3f9d0c9a.jpg)
![processed_2](https://user-images.githubusercontent.com/7643183/119255899-4cc94e00-bbe8-11eb-9517-a37346cca8e3.jpg)
![processed_3](https://user-images.githubusercontent.com/7643183/119255901-4d61e480-bbe8-11eb-8aba-ae02e1c5b9d7.jpg)

# Getting Started

- Clone or download this repository
- Run and feel free to use this app without any account

# Functionalities
- Input the city name. Then you click on the "SEARCH" button.
- Render the searched results as a location(lat, lng, city name).
- From location call api get Forecast Weather.
- Render results as a list of Forecast Weather items.
- Handle configuration changes & lifecycle.
- Handle failures.
- Handle save history search.
- Encrypt and decrypt any internal data caching.

# Libraries and Frameworks

- Presentation module
   - Data-binding
   - MVVM
   - Navigation Graph
- Data module
    - SharePreference
-  Network
    - Retrofit: ver 2.7.1 support kotlin coroutines
    - Okhttp3
    - Gson
- Dependence Injection: Hilt
- Kotlin coroutines
- Layout
    - ConstraintLayout

# Base Architecture Diagram (Model-View-ViewModel)

![architecture_diagram](https://user-images.githubusercontent.com/7643183/119255903-4e931180-bbe8-11eb-95cd-4cd15ed0c3d7.png)

### View

- Activity, Fragment, Views
- Binding data from ViewModel
- Handle UI logic

### View-Model

- Live Data
- Code logic

### Domain use-case

Define all functions to use-case

- Get data from the repository

### Model - Data layer | Repository

All data needed for the application comes from this

Receive a request to get data. Switch data between remote and local to return a value 

- Local data source: Room for complexly functions in the feature
- Remote data source: web service API
- Share preference
# Weather-Forecast-Android
