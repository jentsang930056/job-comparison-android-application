1. When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet1).

- HZ: This is not included in my design. It will be handled entirely by GUI.

2. When choosing to enter current job details, a user will:
a. Be shown a user interface to enter (if it is the first time) or edit all the details of
their current job, which consists of:
i. Title
ii. Company
iii. Location (entered as city and state)
iv. Cost of living in the location (expressed as an index)
v. Yearly salary
vi. Yearly bonus
vii. Training and Development Fund
viii. Leave Time
ix. Telework Days per Week

- HZ: To realize this requirement, I created a "Job" class, and added item i to ix as attributes to the class, including String type attributes ---- "title", "company", "city", "state", and int type attributes ---- "costOfLiving", "yearlySalary", "yearlyBonus", "trainingFund", "leaveTime", "teleworkPerW". I also added a boolean "isCurrentJob" attribute to the class to distinguish current job and future job / job offers. The values will be stored in database after handled by GUI, which is not shown in the design.

b. Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.

- HZ: This is not included in my design. "Save" will be supported by database. "Cancel", "exit", "return to main menu" will be handled entirely by GUI.

3. When choosing to enter job offers, a user will:
a. Be shown a user interface to enter all the details of the offer, which are the same ones listed above for the current job.

- HZ: This requirement will be realized by "Job" class, as mentioned above.

b. Be able to either save the job offer details or cancel.

- HZ: This is not included in my design. "Save" will be supported by database. "Cancel" will be handled by GUI.

c. Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).

- HZ: This is not included in my design. (1) (2) will be handled by GUI. (3) will be supported by database and GUI.

4. When adjusting the comparison settings, the user can assign integer weights to:
a. Yearly salary
b. Yearly bonus
c. Training and Development Fund
d. Leave Time
e. Telework Days per Week
If no weights are assigned, all factors are considered equal.
NOTE: These factors should be integer-based from 0 (no interest/don’t care) to 9 (highest interest)

- HZ: To realize this requirement, I created a "Weights" class, and added int type attributes ---- "yearlySalaryWeight", "yearlyBonusWeight", "trainingFundWeight", "leaveTimeWeight", "teleworkPerWWeight". The values will be stored in database after handled by GUI, which is not shown in the design.

5. When choosing to compare job offers, a user will:
a. Be shown a list of job offers, displayed as Title and Company, ranked from best
to worst (see below for details), and including the current job (if present), clearly
indicated.

- HZ: To realize this requirement, I created a "ComparisonApp" class, and added startCompare(), createJob(), createWeights() methods to this class. I also added a float "score" attribute and a "computeScore(Weights)" method to the "Job" class. Moreover, association relationships were added between 1)"ComparisonApp" class and "Weights" class, 2)"ComparisonApp" class and "Job" class, 3)"Job" class and "Weights" class. When the user select "compare job offers", these methods will be called to generate scores for each job, including job offers and current job. I also added rank() method to "ComparisonApp" class to re-order the jobs from highest score to lowest score. Displaying the list of job offers will be handled by GUI and database.

b. Select two jobs to compare and trigger the comparison.
c. Be shown a table comparing the two jobs, displaying, for each job:
i. Title
ii. Company
iii. Location
iv. Yearly salary adjusted for cost of living
v. Yearly bonus adjusted for cost of living
vi. TDF = Training and Development Fund
vii. LT = Leave Time
viii. RWT = Telework Days per Week

- HZ: To realize this requirement, I added derived int attributes "adjustedYearlySalary" and "adjustedYearlyBonus" to "Job" class, these value will be computed using "yearlySalary" or "yearlyBonus", together with "costOfLiving". The display table will be handled entirely by GUI.

d. Be offered to perform another comparison or go back to the main menu.

- HZ: This is not included in my design. It will be handled entirely by GUI.

6. When ranking jobs, a job’s score is computed as the weighted average of:
AYS + AYB + TDF + (LT * AYS / 260) - ((260 - 52 * RWT) * (AYS / 260) / 8))
where:
AYS = Yearly Salary Adjusted for cost of living
AYB = Yearly Bonus Adjusted for cost of living
TDF = Training and Development Fund ($0 to $18000 inclusive annually) LT = Leave Time (0-100 whole number days inclusive)
RWT = Telework Days per Week
NOTE: The rationale for the RWT subformula is:
-  value of an employee hour = (AYS / 260) / 8
-  commute hours per year (assuming a 1-hour/day commute):
1 * (260 - 52 * RWT)
- therefore travel-time cost = (260 - 52 * RWT) * (AYS / 260) / 8
For example, if the weights are 2 for the yearly salary, 2 for the yearly bonus, 2 for the Training and Development Fund, and 1 for all other factors, the score would be computed as:
2/8 * AYS + 2/8 * AYB + 2/8 * TDF + 1/8 * (LT * AYS / 260) - 1/8 * ((260 - 52 * RWT) * (AYS / 260) / 8)))

- HZ: To realize this requirement, score calculation will be handled by "computeScore(Weights)" method in the "Job" class.

7. The user interface must be intuitive and responsive.
- HZ: This is not included in my design. It will be handled entirely by GUI.

8. For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary).

- HZ: This is not included in my design. No extra features need to be added to the design since no communication or saving between devices is required.
