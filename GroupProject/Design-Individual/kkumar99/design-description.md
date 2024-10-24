# Design Documentation

This directory contains the “design.pdf” document, which consists of the UML class diagram for the requirements highlighted below in this document. There are a total of 4 classes in the UML diagram:
-	MainMenu: Main User Interface Class
-	Job: Class to store current job as well as new job offers.
-	ComparisonWeights: Class to store the active weights set by the user.
-	CompareJobs: Class to handle all job comparisons presented in the requirements.

## Requirements:

The following are all the requirements proposed for this app and details on how the designs addresses these requirements.

1. When the app is started, the user is presented with the main menu, which allows the user to:

    a. Enter or edit current job details.

    b. Enter job offers.
    
    c. Adjust the comparison settings.
    
    d. Compare job offers (disabled if no job offers were entered yet).


-	To address 1(a) and 1(b), the Job class was created which allows the user to create/edit job objects which can be set to either “current” or “offer” by setting the “type” attribute. 
-	ComparisonWeight class was created to address 1(c). This class with store all the proposed weights.
-	CompareJobs class was created to handle compare job offers. More details are documented in requirements below.

##
2.	When choosing to enter current job details, a user will: 
a.	Be shown a user interface to enter (if it is the first time) or edit all the details of their current job, which consists of: 
i.	Title 
ii.	Company 
iii.	Location (entered as city and state) 
iv.	Cost of living in the location (expressed as an index) 
v.	Yearly salary 
vi.	Yearly bonus 
vii.	Training and Development Fund 
viii.	Leave Time 
ix.	Telework Days per Week 
b.	Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.

-	The following features were implemented to address these requirements:
a.	 To address the requirements in 2 (a), the following attributes were added to the Job class:
o	title: string
o	company: string
o	location: string
o	costOfLivingIndex: int
o	salary: float
o	bonus: float
o	trainAndDevFund: float
o	leaveTime: float
o	teleworkDays: int

b.	Save, Cancel and update methods were added to Job class to address this requirement.

##
3.	When choosing to enter job offers, a user will: 
a.	Be shown a user interface to enter all the details of the offer, which are the same ones listed above for the current job. 
b.	Be able to either save the job offer details or cancel.
c.	Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present)

-	All requirements mentioned in this bullet will be implemented via a GUI, the previous two bullets address the classes and attributes created to store all the user provided information.

##
4.	 When adjusting the comparison settings, the user can assign integer weights to: 
a.	Yearly salary 
b.	Yearly bonus
c.	Training and Development Fund
d.	Leave Time
e.	Telework Days per Week 


-	The following attributes were added to the ComparisonWeights class to address this requirement:
a.	salaryWeight: int = 1
b.	bonusWeight: int = 1
c.	trainAndDevFundWeight: int = 1
d.	leaveTimeWeight: int = 1
e.	teleworkWeight: int = 1
-	All weights are initialized to 1, so they are weighted equally in the case when user does not enter any weights. All factors are set to integers and logic will be added in the setter method “updateWeights” to ensure weights are within 0-9.

##
5.	When choosing to compare job offers, a user will: 
a.	Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated. 
b.	Select two jobs to compare and trigger the comparison. 
c.	Be shown a table comparing the two jobs, displaying, for each job: 
i.	Title 
ii.	Company 
iii.	Location 
iv.	Yearly salary adjusted for cost of living.
v.	Yearly bonus adjusted for cost of living. 
vi.	TDF = Training and Development Fund 
vii.	LT = Leave Time 
viii.	RWT = Telework Days per Week 
d.	Be offered to perform another comparison or go back to the main menu.

-	All requirements mentioned in this bullet, will be implemented via the GUI and are therefore not mentioned in the UML class diagram.

##
6.	When ranking jobs, a job’s score is computed as the weighted average of: 

    AYS + AYB + TDF + (LT * AYS / 260) - ((260 - 52 * RWT) * (AYS / 260) / 8)) 

    where: 

    o	AYS = Yearly Salary Adjusted for cost of living 
    
    o	AYB = Yearly Bonus Adjusted for cost of living 
    
    o	TDF = Training and Development Fund ($0 to $18000 inclusive annually) 
    
    o	LT = Leave Time (0-100 whole number days inclusive) 
    
    o	RWT = Telework Days per Week

-	CalculateJobRank(jobID: int) method was added to CompareJobs class to address this requirement. Each job can be passed through this method to calculate its score/rank. The formula in the requirements will be implemented within this method.

##
7.	The user interface must be intuitive and responsive.
-	No representation needed in UML diagram. Not a technical requirement.

##
8.	For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary).
-	Class diagram assumes single system design.
