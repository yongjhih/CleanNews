[![Stories in Ready](https://badge.waffle.io/pardom/CleanNews.png?label=ready%20for%20dev&title=Ready)](https://waffle.io/pardom/CleanNews)
[![Build Status](https://travis-ci.org/pardom/CleanNews.svg?branch=master)](https://travis-ci.org/pardom/CleanNews)

# CleanNews
Hacker News client written with [Clean Architecture](https://blog.8thlight.com/uncle-bob/2012/08/13/the-clean-architecture.html) and [Reactive Presentation Model](http://cycle.js.org/).

## Project structure

#### Core
* Entities - business logic

#### App
* Use cases - application specific logic
* DataSource interfaces

#### Data
* DataSource implementations

#### Presentation
* Presentation models
* Navigation interfaces

#### Android
* Views
* Navigation implementations

#### Desktop
* Views
* Navigation implementations
