# Assignment 5 Design Description

This document gives a detailed explanation of the job offer comparison app. The app is created to help users compare job offers easily. It allows users to input and update job details, set preferences for comparison, and see which job offers are best based on their settings. The design organizes the app's features into clear parts that work together to make comparing job offers straightforward and user-friendly.

## Classes

The UML class diagram includes the following classes:

- `JobDetails` (with subclasses `CurrentJob` and `JobOffer`)
- `JobComparison`
- `ComparisonSettings`

Each class is designed to encapsulate specific functionalities and data necessary for the application.

## Requirements

### 1: Navigating the Main Menu
The main menu is the first interface users interact with upon starting the app. It provides access to four main functionalities:

- Entering and editing current job details
- Entering job offers
- Adjusting comparison settings
- Comparing job offers

Each option in the main menu is associated with a different class:
- `CurrentJob` and `JobOffer` (both subclasses of `JobDetails`) handle entering and editing job details.
- `ComparisonSettings` manages how job features are weighed.
- `JobComparison` is responsible for comparing the job offers.

### 2: Entering and Editing Current Job Details
The user can enter or edit details of their current job, including job title, company, location, cost of living, salary, bonus, training fund, leave time, and telework days.

`CurrentJob` subclass of `JobDetails` includes methods `saveDetails()`, `editDetails()`, and `cancel()`, which manage the entry and modification of current job details stored in the attributes inherited from `JobDetails`.

### 3: Entering Job Offers
The user can enter details for job offers similar to those for the current job and either save or cancel these entries.

`JobOffer`, another subclass of `JobDetails`, includes methods `saveOffer()` and `cancelOffer()`, allowing the user to manage job offer entries with the same attributes as the current job.

### 4: Adjusting Comparison Settings
The user can change the importance of different job features like salary, bonus, training opportunities, leave time, and telework days.

`ComparisonSettings` lets users set how much each job feature matters in their job comparison through the `setWeights()` method. Here’s what users can adjust:
- `salaryWeight`: Shows how important the salary is when comparing jobs.
- `bonusWeight`: Shows how important the bonus is when comparing jobs.
- `trainingFundWeight`: Shows how important training funds are when comparing jobs.
- `leaveTimeWeight`: Shows how important leave time is when comparing jobs.
- `teleworkDaysWeight`: Shows how important the ability to work from home is when comparing jobs.

### 5: Comparing Job Offers
The user can compare job offers, including the current job, based on the weighted aspects. The results are displayed in a ranked list from best to worst.

`JobComparison` class includes a method `rankJobs()` that ranks jobs based on their weighted scores, `selectJobsToCompare()` that allows the user to select specific jobs for comparison, and `displayComparisonTable()` that shows the comparison results in a detailed table format.

### 6: Comparing Job Calculation
The calculation uses weights from the `ComparisonSettings` to decide how much each factor should impact the overall score. This means if the user values salary more than other factors, it will have a bigger effect on the job's score.

This scoring helps users quickly see which jobs fit their needs and preferences best, aiding them in making informed job choices.

### 7: Intuitive and Responsive User Interface
The user interface must be easy to use and work smoothly.

This part of the requirement isn't shown in the UML diagram because it relates more to how the app looks and feels when used, which will be addressed during the app's development phase, not in the initial design phase.

### 8: Additional Information
The design is made for a single user and doesn't require saving data or using multiple devices. This keeps the design simple and focuses on the main features.

This explanation with the class diagram covers everything the app needs to do and gives developers a clear plan for building it.