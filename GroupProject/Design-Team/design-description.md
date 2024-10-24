# Design Description for Requirements -Version 2.0

**1. When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet1).**
<br>
<br>
**Main** class was created to initiate the app with `main()` method and show the main menu window. From the main menu, user can select from actions (1)(2)(3)(4), this part is handled by GUI.<br>
D3 updates: <br>
All ui activity classes and **ComparisonApp** class were combined and showed in the **Main** class to show the critical methods.
##
**2. When choosing to enter current job details, a user will:
a. Be shown a user interface to enter (if it is the first time) or edit all the details of their current job, which consists of:
i. Title
ii. Company
iii. Location (entered as city and state)
iv. Cost of living in the location (expressed as an index)
v. Yearly salary
vi. Yearly bonus
vii. Training and Development Fund
viii. Leave Time
ix. Telework Days per Week**<br>
<br>
To realize this requirement,  **Job** class was created, a association relationship was added between this class and **Main** class, and item i to ix were added as attributes to the class, including String type attributes  `title` `company` `city` `state`, int type attributes `jobID` `costOfLiving` `teleworkPerW` and float type attributes `yearlySalary` `yearlyBonus` `trainingFund` `leaveTime`. A boolean `isCurrentJob` attribute was also added to the class to distinguish current job and job offers. The values will be stored in database after handled by GUI, which is not shown in the design.

**b. Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.**<br>
<br>
`saveJob(Job)` `updateJob(Job)` methods were added to **Job** class to allow the user to save the job details after entering or editing the current job. "Cancel and exit, return to main menu" will be handled by GUI.<br>
D3 updates:<br>
A DAO interface class **JobDAO** was created to perform database operations of a persistent Job library and methods originally in **Job** class were moved here. `insertJob(Job)` `updateJob(Job)` `deleteJob(Job)` `getAll()` `getJobByID(long)` `getCurrentJob()` `getAllJobSortedByScore()` operations were added to this class. A ROOM database class **AppDatabase** was also added to fulfill the database part, and a `jobDAO` attribute was added in this class to handle the job library. A `database` attribute and `createJob()` method were added to **Main** class.<br>
Association relationships were added between **Job** and **JobDAO** classes, **JobDAO** and **AppDatabase** classes, **AppDatabase** and **Main** classes.


##
**3. When choosing to enter job offers, a user will:
a. Be shown a user interface to enter all the details of the offer, which are the same ones listed above for the current job.**<br>
<br>
This requirement will also be realized by **Job** class, the same as **2a**.

**b. Be able to either save the job offer details or cancel.**<br>
<br>
The same as **2b**.

**c. Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).**<br>
<br>
(1) will be handled by `saveJob(Job)` method and (2) will be handled by GUI.
To realize (3), **ComparisonApp** class was created and `compareTwoJobs()` method was added to compare the offer with current job.  Association relationships were added between **ComparisonApp** class and **Job** class, as well as between **ComparisonApp** class and **Main** class. The info. display will be handled by GUI.<br>
D3 updates:<br>
(1) will be handled by `insertJob(Job)` in **JobDAO** class instead of `saveJob(Job)` method. **ComparisonApp** class was merged to **Main** class, so (3) will be realized by `compareTwoJobs()` method added in **Main** class.

##
**4. When adjusting the comparison settings, the user can assign integer weights to:
a. Yearly salary
b. Yearly bonus
c. Training and Development Fund
d. Leave Time
e. Telework Days per Week
If no weights are assigned, all factors are considered equal. NOTE: These factors should be integer-based from 0 (no interest/don’t care) to 9 (highest interest)**<br>
<br>
To realize this requirement, **Weights** class was created, a association relationship was added between this class and **Main** class, and int type attributes `yearlySalaryWeight` `yearlyBonusWeight` `trainingFundWeight` `leaveTimeWeight` `teleworkPerWWeight` were added to this class. `updateWeight(Weights)` method was added to update these weight values in database.<br>
D3 updates:<br>
An attribute `weightsId` was also added to **Weights** class. A DAO interface class **WeightsDAO** was created to perform database operations of a persistent Weights library and methods originally in **Weights** class were moved here. `insertWeights(Weights)` `getWeights()` operations were added to this class. A `weightsDAO` attribute was added to **AppDatabase** class to handle the Weights library. In **Main** class, `updateWeights()` method was added.<br>
Association relationships were added between **Weights** and **WeightsDAO** classes, **WeightsDAO** and **AppDatabase** classes.

##
**5. When choosing to compare job offers, a user will:
a. Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.**<br>
<br>
To realize this requirement, `rank()` `computeScore()` `createJob()` `createWeights()` methods were added to **ComparisonApp** class to calculate the score for each job and re-order the list of jobs by score. A float `score` attribute was added to **Job** class to track the score for each job. Moreover, an association relationship was added between **ComparisonApp** class and **Weights** class. Displaying the list of job offers will be handled by GUI and database.<br>
D3 updates:<br>
**ComparisonApp** class was merged to **Main** class, `rank()` `computeScore()` methods were added here. No new relationship was added.


**b. Select two jobs to compare and trigger the comparison.**<br>
<br>
User will select two jobs from the job list and trigger the comparison at GUI by pressing a "compare" button to call `compareTwoJobs()` method under **ComparisonApp** class.<br>
D3 updates:<br>
**ComparisonApp** class was merged to **Main** class.

**c. Be shown a table comparing the two jobs, displaying, for each job:
i. Title
ii. Company
iii. Location
iv. Yearly salary adjusted for cost of living
v. Yearly bonus adjusted for cost of living
vi. TDF = Training and Development Fund
vii. LT = Leave Time
viii. RWT = Telework Days per Week**<br>
<br>
Derived float attributes `adjustedYearlySalary` `adjustedYearlyBonus` were added to **Job** class, which will be computed using `yearlySalary` and `costOfLiving` attributes, or `yearlyBonus` and `costOfLiving` attributes. The display table will be handled entirely by GUI.

**d. Be offered to perform another comparison or go back to the main menu.**<br>
<br>
This is not included in the design. It will be handled entirely by GUI.
##
**6. When ranking jobs, a job’s score is computed as the weighted average of:
AYS + AYB + TDF + (LT * AYS / 260) - ((260 - 52 * RWT) * (AYS / 260) / 8))
where:
AYS = Yearly Salary Adjusted for cost of living
AYB = Yearly Bonus Adjusted for cost of living
TDF = Training and Development Fund ($0 to $18000 inclusive annually) LT = Leave Time (0-100 whole number days inclusive)
RWT = Telework Days per Week
NOTE: The rationale for the RWT subformula is:**
**-  value of an employee hour = (AYS / 260) / 8**
**-  commute hours per year (assuming a 1-hour/day commute):
1 * (260 - 52 * RWT)**
**- therefore travel-time cost = (260 - 52 * RWT) * (AYS / 260) / 8**
**For example, if the weights are 2 for the yearly salary, 2 for the yearly bonus, 2 for the Training and Development Fund, and 1 for all other factors, the score would be computed as:
2/8 * AYS + 2/8 * AYB + 2/8 * TDF + 1/8 * (LT * AYS / 260) - 1/8 * ((260 - 52 * RWT) * (AYS / 260) / 8)))**<br>
<br>
Score calculation will be realized within `computeScore()` method in the **ComparisonApp** class. Details were not shown in the design.<br>
D3 updates:<br>
**ComparisonApp** class was merged to **Main** class.
##
**7. The user interface must be intuitive and responsive.**<br>
<br>
This is not included in the design. It will be handled entirely by GUI.
##
**8. For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary).**<br>
<br>
No extra features need to be added to the design since no communication or saving between devices is required.
