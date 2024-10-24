# Requirements

1. When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet). 

   ```
   When the app is started, Job class and ComparisionSetting class are inititated. We can use internal method to update the JobOffer[]. We can also compare jobs offers by calling rankAll() or compare(JobOffer, JobOffer) method.
   ```

2. When choosing to *enter current job details,* a user will:

   1. Be shown a user interface to enter (if it is the first time) or edit all the details of their current job, which consists of:
      1. Title
      2. Company
      3. Location (entered as city and state)
      4. Cost of living in the location (expressed as an [index](https://www.expatistan.com/cost-of-living/index/north-america))
      5. Yearly salary 
      6. Yearly bonus 
      7. Training and Development Fund
      8. Leave Time
      9. Telework Days per Week
   2. Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.

   ````
   We can call saveJobOffer() or editJobOffer() to enter or edit current job details. When entering current jobs, we have an additional Boolean atrribute called isCurrent in aother CurrentJob class and set it to True.
   ````

   

3. When choosing to *enter job offers,* a user will:

   1. Be shown a user interface to enter all the details of the offer, which are the same ones listed above for the current job.
   2. Be able to either save the job offer details or cancel.
   3. Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).

   ````
   This is bascially the same as entering current job. When entering job offers, the isCurrent in CurrentJob Class is set to False.
   ````

   

4. When *adjusting the comparison settings,* the user can assign integer *weights* to:

   1. Yearly salary
   2. Yearly bonus
   3. Training and Development Fund
   4. Leave Time
   5. Telework Days per Week

   ````
   After initializing ComparisonSetting class. We can call changeWeights() to assign integer weights to each different attribute.
   ````

   

If no weights are assigned, all factors are considered equal.

NOTE: These factors should be integer-based from 0 (no interest/don’t care) to 9 (highest interest)

5. When choosing to *compare job offers,* a user will:

   Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.

   ````
   calling rankAll() will rank the current JobOffer[] and return a list through internal computation.
   ````

   Select two jobs to compare and trigger the comparison.

   ````
   calling compare(JobOffer, JobOffer) method will return a list of 2 JobOffer ranked with score.
   ````

   1. Be shown a table comparing the two jobs, displaying, for each job:

      1. Title

      2. Company
      3. Location 
      4. Yearly salary adjusted for cost of living
      5. Yearly bonus adjusted for cost of living
      6. TDF = Training and Development Fund
      7. LT = Leave Time 
      8. RWT = Telework Days per Week 

   ````
   Other basic attributes are in each JobOffer returned by calling rankAll() method.
   ````

   2. Be offered to perform another comparison or go back to the main menu.

      ````
      calling compare(JobOffer, JobOffer) method will return a list of 2 JobOffer ranked with score.
      ````

6. When ranking jobs, a job’s score is computed as the **weighted** average

````
The internal design of rankAll()
````

7. The user interface must be intuitive and responsive.

   ````
   Main Class is Job.
   ````

   

8. For simplicity, you may assume there is a *single system* running the app (no communication or saving between devices is necessary).

   ````
   all class connected once started.
   ````

   