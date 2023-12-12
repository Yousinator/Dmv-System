<br/>
<p align="center">
  <a href="https://github.com/Yousinator/dmv-system">
    <img src="https://github.com/ShaanCoding/ReadME-Generator/blob/main/images/logo.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">DMV System</h3>

  <p align="center">
    A fully functional DMV system I made for my "Programming" course final. 
    <br/>
    <br/>
    <a href="https://github.com/Yousinator/dmv-system"><strong>Explore the docs »</strong></a>
    <br/>
    <br/>
    <a href="https://github.com/Yousinator/dmv-system/issues">Report Bug</a>
    .
    <a href="https://github.com/Yousinator/dmv-system/issues">Request Feature</a>
  </p>
</p>
<p align="center">
  <a href="">
<img src="https://img.shields.io/github/downloads/Yousinator/dmv-system/total"> <img src ="https://img.shields.io/github/contributors/Yousinator/dmv-system?color=dark-green"> <img src ="https://img.shields.io/github/forks/Yousinator/dmv-system?style=social"> <img src ="https://img.shields.io/github/stars/Yousinator/dmv-system?style=social"> <img src ="https://img.shields.io/github/license/Yousinator/dmv-system">
  </a>
</p>

## Table Of Contents

- [About the Project](#about-the-project)
- [Built With](#built-with)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Authors](#authors)
- [Acknowledgements](#acknowledgements)

## About The Project

![Screen Shot](https://i.pinimg.com/originals/9c/8c/db/9c8cdbb2bd7b637edd5b3a767b74153a.gif)

This project is a Java-based application simulating a Car Department of Motor Vehicles (DMV) system. It is designed to manage car and user data, offering different user roles like customers, admins, and roots, each with specific privileges. The project integrates a SQLite database to persistently store and manage data, and utilizes Maven for dependency management.

## 💡Detailed INFO:

The Car DMV System serves as a comprehensive platform for handling vehicle-related administrative tasks. Key features and functionalities include:

- **User Authentication**: Different user roles (customer, admin, root) with secure login functionalities.
- **Data Management**: Allows admins and root users to add, modify, and delete customer and car records.
- **Car Management**: Customers can view and manage their car details, such as make, model, year, color, and more.
- **Role-Based Access Control**: The system implements role-based access control to segregate duties and permissions among different types of users.
- **Database Integration**: Uses SQLite for efficient data storage and retrieval, ensuring data persistence and integrity.
- **Maven Support**: The project is built and managed with Maven, simplifying dependency management and project compilation.
- **User Interface**: Includes a user-friendly interface for easy navigation and operation of the system.

This system is ideal for educational purposes, illustrating how a real-world application might handle user roles and data management, and serves as an excellent example of integrating Java with a database and Maven.

## Built With

For this project, Java was the primary language used, especially for developing the graphical user interface (GUI) components. Java's robustness and versatility make it an ideal choice for creating interactive and user-friendly applications. Additionally, Maven was used as a build tool for managing dependencies, and SQLite for database management, ensuring efficient data storage and retrieval.

<p align="center">
  <a href="">
    <img src="https://img.shields.io/badge/Written%20in-Java-red.svg">
    <img src="https://img.shields.io/badge/Build%20Tool-Maven-C71A36.svg">
    <img src="https://img.shields.io/badge/Database-SQLite-003B57.svg">
  </a>
</p>

For this project, I used both C lang and Java. The C code is mainly for me as I like the language and I want to use it. As for Java, I used it to provide a GUI for each code.

## Getting Started

Getting started with this repository is very simple. You can just navigate to the section you'd like to check out and you'll find documentation for each algorithm in its specific file. Each topic will have a java joint GUI program.

### Prerequisites

- Java Development Kit (JDK): The application is built using Java, thus requiring JDK to be installed on your system.
- Maven: Used for managing project dependencies.

##

If you are not familiar with Java make sure to check out Java0x01 written by smadi0x86 and me:

[![Readme Card](https://github-readme-stats.vercel.app/api/pin/?username=smadi0x86&repo=Java0x01&show_owner=true&theme=dark)](https://github.com/smadi0x86/Java0x01)

##

### Installation

1. Clone the repo

```sh
git clone https://github.com/yousinator/dmv-system.git
```

2. Navigate to the project directory and use Maven to compile the project:

```sh
mvn compile
```

3. Run the application:

```sh
mvn exec:java -Dexec.mainClass=com.yousinator.Main
```

## Usage

The Car DMV System allows users to perform the following actions:

- **Customers**: View and manage their car details.
- **Admins**: Manage customer data, including adding, removing, or updating customer and car information.
- **Root**: Has all admin privileges, plus managing admin users.

## Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

- If you have suggestions for adding or removing projects, feel free to [open an issue](https://github.com/Yousinator/dmv-system/issues/new) to discuss it, or directly create a pull request after you edit the _README.md_ file with necessary changes.
- Please make sure you check your spelling and grammar.
- Create individual PR for each suggestion.
- Please also read through the [Code Of Conduct](https://github.com/Yousinator/dmv-system/blob/main/CODE_OF_CONDUCT.md) before posting your first idea as well.

### Creating A Pull Request

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

Distributed under the MIT License. See [LICENSE](https://github.com/Yousinator/dmv-system/blob/main/LICENSE.md) for more information.

## Authors

- **Yousinator** - _AI Student_ - [Yousinator](https://github.com/Yousinator/) - _Wrote the codes and README_\

## Acknowledgements

- [Yousinator](https://github.com/Yousinator)

### Notes:

1. **About the Project**: Elaborate on the purpose, features, and functionalities of your project.
2. **Built With**: List all significant technologies and frameworks used in the project.
3. **Getting Started**: Provide detailed instructions on setting up the project locally. Adjust paths and commands to match your project's structure.
4. **Usage**: Describe how to use the application, including any important commands.
5. **Contributing**: Encourage other developers to contribute to your project.
6. **License**: Specify the license under which the project is released.
7. **Authors**: Credit yourself and any other contributors.
8. **Acknowledgements**: Acknowledge any individual, institution, or resource that helped you in your project.

Feel free to modify and expand each section to suit your project’s specifics.
