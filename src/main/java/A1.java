// Java Program to illustrate various scheduling algorithms. Class A1 reads the data from file provided by the
// user and applies scheduling algorithms to check the values such as total turnaround time, weight time for each process.
//
//public static void main(String[] args): method to accept the file name from the user and read the data from the file
//precondition: variables are declared.
//postcondition: data read from the file and trasferred to array so that it can be passed to the functions.
//
////static float[] averageFCFS = checkFCFS(i) : method to check the FCFS scheduling, it returns average turnaround time and average weight time
//precondition-data from the file is made availble to this function for the FCFS calculation
//postcondition-displays the FCFS values as asked in asssignment and returns the average turnaround time and average wait time
//
//static float[] averageSPN = checkSPN(i): method to check the SPN scheduling,it returns average turnaround time and average weight time
//precondition-data from the file is made availble to this function for the SPN calculation
//postcondition-displays the SPN values as asked in asssignment and returns the average turnaround time and average wait time
//
//static float[] averagePP = checkPP(i): method to check the PP scheduling,it returns average turnaround time and average weight time
//precondition-data from the file is made availble to this function for the PP calculation
//postcondition-displays the PP values as asked in asssignment and returns the average turnaround time and average wait time
//
//static float[] averagePRR = checkPRR(i): method to check the PRR scheduling,it returns average turnaround time and average weight time
//precondition-data from the file is made availble to this function for the PRR calculation
//postcondition-displays the PRR values as asked in asssignment and returns the average turnaround time and average wait time
//
//static printavg(averageFCFS,averageSPN,averagePP,averagePRR): method to print the average - turnaround time and wait time
//precondition-All the functions for the listed scheduling algorithms have executed and returned the average values
//postcondition-prints the average values
//
import java.io.*;
import java.lang.String;

public class A1
{
    static int i=0;

    public static void main(String[] args)throws Exception
    {
        int disp=0;
        //ASSUMPTION : THERE ARE 20 OR LESS RECORDS IN THE INPUT FILE
        int es[]=new int[20]; //reads execution size
        int at[]=new int[20];//reads arrival time
        int p[]= new int[20];//reads process id
        int pr[]=new int[20];//reads priority

        System.out.print('\u000C');
        String inFile=" " ;
        if (args.length==0) {
            System.out.println("no arguments were given");
        }
        else {
            inFile=args[0];
        }
        String myStr;

        try{FileReader file= new FileReader(inFile);
            BufferedReader br = new BufferedReader(file); //read the file

            while((myStr = br.readLine()) != null)   //read the file line by line
            {
                if(myStr.contains("DISP"))
                    disp=(Integer.valueOf(myStr.substring(6)));//extracts the value of disp and send it to the setter method

                if(myStr.contains("ID"))
                    p[i]=(Integer.valueOf(myStr.substring(5)));//extracts the value of process id and send it to the setter method

                if(myStr.contains("Arrive"))
                    at[i]=(Integer.valueOf(myStr.substring(8)));//extracts the value of arrival time and send it to the setter method

                if (myStr.contains("ExecSize"))
                    es[i]=(Integer.valueOf(myStr.substring(10)));//extracts the value of execution size and send it to the setter method

                if(myStr.contains("Priority")){
                    pr[i]=(Integer.valueOf(myStr.substring(10)));//extracts the value of process priority and send it to the setter method
                    i++; //moves to the next process details,untill the last process details are read
                }
            }
        } catch (IOException e){
            System.out.println(" Error opening the file " + inFile);//file exception handling
            System.exit(0);}

        float[] averageFCFS = checkFCFS(disp,p,at,es,pr,i);   //function call to check FCFS algorithm return average turnaround and wait time
        float[] averageSPN = checkSPN(disp,p,at,es,pr,i);     //function call to check SPN algorithm return average turnaround and wait time
        float[] averagePP = checkPP(disp,p,at,es,pr,i);       //function call to check PP algorithm return average turnaround and wait time
        float[] averagePRR = checkPRR(disp,p,at,es,pr,i);     //function call to check PRR algorithm return average turnaround and wait time
        printavg(averageFCFS,averageSPN,averagePP,averagePRR);//function call to print the average values as returned by above functions
    }

    public static float[] checkFCFS(int disp,int p[],int at[],int es[],int pr[],int n )
    {
        int st=0;//start time
        int ft[]=new int[n];		//finish time
        int wt[]=new int[n];		//wait time
        int tt[]=new int[n]; 		//turnaround time
        int totaltt=0;				//total turnaround time
        int totalwt=0;				//total wait time
        float avgtt=0;				//average turnaround time
        float avgwt=0;				//average wait time

        System.out.println(" FCFS:" );
        st=0;
        for(int j=0;j<n;j++)
        {
            st=st+disp;    			//start time of the process = start time of the process + disp
            ft[j]= st+es[j];  		//finish time of the process = start time of the process+execution size of the process +disp
            tt[j]=ft[j]-at[j];   	//turnaround time of the process=finish time of the process - arrival time of the process
            wt[j]=tt[j]-es[j];   	//wait time of the process=turnaround time - execution size

            System.out.println(" "+"T" + (st)+":" + "p"+p[j]+ "(" + pr[j] +")"  );
            st=ft[j];       			//start time of next process = finish time of the current process
            totaltt+=tt[j];     		//total turnaround time=turnaround time of all processes
            totalwt+=wt[j];      	//total wait time=wait time of all processes
        }
        System.out.println("\n");
        System.out.println(" Process Turnaround Time Waiting Time" );
        for(int j=0;j<i;j++)
            System.out.println(" "+ "p" + p[j]+ "\t\t" + tt[j] + "\t\t" + wt[j]);

        avgtt=(float)totaltt/(float)n;	//average turnaround time = total turnaround time divided by number of processes
        avgwt=(float)totalwt/(float)n;	//average wait time = total wait time divided by number of processes
        float[] avg={avgtt,avgwt};		//store the average turnaround time and average wait time in an array
        return avg;						//return the array containing average turnaround time and average wait time
    }

    public static float[] checkSPN(int disp,int p[],int at[],int es[],int pr[],int n )
    {
        int ft[]=new int[n];						//finish time
        int wt[]=new int[n];						//wait time
        int tt[]=new int[n]; 						//turnaround time
        int totaltt=0;							//total turnaround time
        int totalwt=0;							//total wait time
        float avgtt=0;							//average turnaround time
        float avgwt=0;							//average wait time
        int st=0;                                 //system time
        int f[] = new int[6];                     // flag to check process is completed or not
        int tot=0;                                //counter to count processs numbers being executed
        boolean a = true;                         //flag to check if all the processes have completed execution

        System.out.println("\n");
        System.out.println(" SPN:" );

        while(true)
        {
            int c=n, min=999;
            if (tot == n)                           // total no of process = completed process loop will be terminated
                break;

            for (int i=0; i<n; i++)                 //continue untill all processes are executed
            {
                // If i'th process arrival time <= system time and its flag=0 and execution size<min
                // That process will be executed first
                if ((at[i] <= st) && (f[i] == 0) && (es[i]<min))
                {
                    min=es[i];
                    c=i;
                }
            }
            /* If c==n means c value can not updated because no process arrival time< system time so we increase the system time */
            if (c==n)
                st++;
            else					//if process arrives
            {
                st=st+disp;         //system time=system time+disp
                ft[c]=st+es[c];     //finish time of the process=system time+execution size of the process
                tt[c]=ft[c]-at[c];  //turnaround time of the process=finish time of the process -arrival time of the process
                wt[c]=tt[c]-es[c];  //wait time of the process=turnaround time of the process-execution size of the process
                f[c]=1;             //flag of the process is set to 1
                tot++;              //counts the process completed
                System.out.println(" " +"T" + (st)+":" + "p" + p[c]+ "(" + pr[c] +")"  );
                st=ft[c];           //system time is incremented to finish time of the process
            }
        }
        System.out.println("\n");
        System.out.println(" Process Turnaround Time Waiting Time" );
        for(int i=0;i<n;i++){
            System.out.println("  " +"p"+ p[i]+ "\t\t" + tt[i] + "\t\t" + wt[i]);
            totaltt+= tt[i];               //total turnaround time = sum of turnaround time of all processes
            totalwt+= wt[i];               //total wait time = sum of wait time of all processes
        }
        avgtt=(float)totaltt/ (float)n;    //average turnaround time=total turnaround time/number of processes
        avgwt=(float)totalwt/(float)n;     //average wait time=total wait time/number of processes
        float[] avg={avgtt,avgwt};         //store the average turnaround time and average wait time in an array
        return avg;                        //return the array containing average turnaround time and average wait time
    }

    public static float[] checkPP(int disp,int p[],int at[],int es[],int pr[],int n )
    {
        int ft[]=new int[n];				//finish time
        int wt[]=new int[n];				//wait time
        int tt[]=new int[n]; 				//turnaround time
        int totaltt=0;						//total turnaround time
        int totalwt=0;						//total wait time
        float avgtt=0;						//average turnaround time
        float avgwt=0;						//average wait time
        int rem_es[] = new int[n];          //remaining execution size
        int is_completed[] = new int[n];    //completed execution
        int current_time = 0;               //current time
        int completed = 0;                  //process which completed execution
        int st=0;                           //start time
        int fin=0;                          //additional time counter for addition of disp upon process completion

        System.out.println("\n");
        System.out.println(" PP:" );
        for (int i = 0 ; i < n ; i++)
            rem_es[i] =  es[i];             //store the value of execution size into remaining execution size

        for(int i = 0 ; i < n ; i++)
            is_completed[i] =  0;           //initialise is_completed to 0

        // until all the processes are not completed
        while(completed != n) {
            int j = -1;
            int mx = 100;
            //check the incoming process arrival time, if the arrival time is less than or equal to current time and the
            //process is not completed, then it should be picked up for execution
            for(int i = 0; i < n; i++) {
                if(at[i] <= current_time && is_completed[i] == 0) {
                    if(pr[i] < mx) {        //check the priority of the current process is less than the priority of previous process
                        mx = pr[i];         //assin mx the current process priority
                        j = i;              //assign the current process id to j
                    }
                    if(pr[i] == mx) {       //check the priority of the current process is equal to priority of previous process
                        if(at[i] < at[j]) { //if arrival time of the current process is less than the arrival time of previous process
                            mx = pr[i];     //assign mx priority of current process
                            j = i;          //assign j the process id of the current process
                        }
                    }
                }
            }  //pass the process selected based on the above condition for execution
            if(j != -1) {
                if(rem_es[j] == es[j]) {     //if remaining execution size is equal to execution size of the process
                    if(current_time < fin)   //if the current process is being picked up post completion of previous process
                        current_time=fin;    //assign the value of fin to current time
                    else
                        current_time=current_time+disp;//else current time =current time +disp

                    st=current_time;        //update start time of the process to current time
                    rem_es[j]-=1;           //remaining execution size is reduced by 1
                    current_time++;         //current time is incremented by 1 to track the processing time of the process
                    fin=current_time;       //finish time counter is updated to current time
                }
                else{                       //if the current process is a previously partially executed process
                    if(current_time < fin)  //if the current process is being picked up post completion of previous process
                        current_time=fin;   //assign the value of fin to current time

                    st=current_time;        //update start time of the process to current time
                    rem_es[j]-=1;
                    current_time++;
                    fin=current_time;
                }
                System.out.println(" " +"T" + st +":" + "p"+p[j]+ "(" + pr[j] +")");
                if (rem_es[j]==0)           //if the process has completed execution
                {
                    ft[j]=current_time;     //finish time of the process is equal to current time
                    tt[j]=ft[j]-at[j];      //turnaround time of the process is equal to finish time of the process -arrival time of the process
                    wt[j]=tt[j]-es[j];      //wait time of the process is equal to turnaroung time of the process -execution size of the process

                    totaltt+=tt[j];         //total turnaround time is sum of turnaround time of all processes
                    totalwt+=wt[j];         //total wait time is sum of wait time of all processes

                    is_completed[j]=1;      //is completed flag is set to 1 for the current process
                    completed++;            //number of processes completed is incremented by 1
                    fin=current_time+disp;  //fin time is incremented to add disp value to current time
                }

            }
            else
                current_time++;

        }

        System.out.println("\n");
        System.out.println(" Process Turnaround Time Waiting Time" );
        for (int i=0; i<n; i++)
            System.out.println(" " + "p" + p[i] + "\t\t" + tt[i] + "\t\t "+  wt[i]);

        avgtt=(float)totaltt/(float)n;             //average turnaround time=total turnaround time/number of processes
        avgwt=(float)totalwt/(float)n;             //average wait time=total wait time/number of processes
        float[] avg={avgtt,avgwt};
        return avg;
    }

    public static float[] checkPRR(int disp,int p[],int at[],int es[],int pr[],int n )
    {
        int ft[]=new int[n];					//finish time
        int wt[]=new int[n];					//wait time
        int tt[]=new int[n]; 					//turnaround time
        int totaltt=0;							//total turnaround time
        int totalwt=0;							//total wait time
        float avgtt=0;							//average turnaround time
        float avgwt=0;							//average wait time
        int rem_es[] = new int[n];              //remaining execution size
        int quantum[]= new int[n];              // quantum declaration as defined in the assignment
        int t = disp;                           // Current time
        int st=disp;                            //start time of the process

        System.out.println("\n");
        System.out.println(" PRR:" );
        for (int i=0;i<n;i++)                   //HPC has a quantum of 4 and LPC has a quantum of 2
        {
            if (pr[i] <= 2)                     //check priority of the process and assign quantum
                quantum[i]=4;
            else if(pr[i]>2)
                quantum[i]=2;
        }

        for (int i = 0 ; i < n ; i++)
            rem_es[i] =  es[i];                 //assign value of execution size to remaining execution size

        // Keep traversing processes in round robin manner
        // until all of them are not done.
        while(true)
        {
            boolean done = true;
            // Traverse all processes one by one repeatedly
            for (int i = 0 ; i < n; i++)
            {
                // If remaining execution size of a process is greater than 0
                // then only need to process further
                if (rem_es[i] > 0)
                {
                    done = false; // There is a pending process

                    if (rem_es[i] > quantum[i]&& at[i] < st)
                    {
                        st=t;
                        // Decrease the remaining execution_size of current process by quantum
                        rem_es[i] -= quantum[i];

                        // Increase the value of t i.e. shows how much time a process has been processed
                        t = t+disp+quantum[i];
                        System.out.println(" " +"T" + st +":" + "p"+p[i]+ "(" + pr[i] +")" );
                        st=t;
                    }

                    // If execution_size is smaller than or equal to quantum. Last cycle for this process
                    else if (rem_es[i] <= quantum[i]&& at[i] < st )
                    {
                        st=t;
                        // Increase the value of t i.e. shows how much time a process has been processed
                        t = t + rem_es[i]+disp;

                        // Waiting time is current time minus time used by this process
                        if(es[i]<=quantum[i])
                            wt[i]=st-at[i];
                        else
                            wt[i]=((t-at[i])-es[i])-disp;

                        // As the process gets fully executed make its remaining execution size = 0
                        rem_es[i] = 0;
                        System.out.println(" " +"T" + st +":" + "p" + p[i]+ "(" + pr[i] +")");
                    }
                }

            }
            // If all processes are done
            if (done == true)
                break;
        }
        // calculating turnaround time by adding execution size of the process + wait time of the process
        for (int i = 0; i < n ; i++)
            tt[i] = es[i] + wt[i];
        System.out.println("\n");
        System.out.println(" Process Turnaround Time Waiting Time" );
        for (int i=0; i<n; i++)
        {
            totalwt = totalwt + wt[i];
            totaltt = totaltt + tt[i];
            System.out.println(" " + "p" + p[i] + "\t\t" + tt[i] + "\t\t "+  wt[i]);
        }
        avgwt = (float)totalwt / (float)n;
        avgtt = (float)totaltt / (float)n;
        float[] avg={avgtt,avgwt};
        return avg;
    }

    public static void printavg(float[] averageFCFS,float[] averageSPN, float[] averagePP,float[] averagePRR)
    {
        System.out.println("\n");
        System.out.println(" Summary");
        System.out.println(" Algorithm  Average Turnaround Time  Average Waiting Time" );

        System.out.printf("  FCFS" +"\t\t" );for (float f:averageFCFS) System.out.printf(" %6.2f \t\t\t", f);
        System.out.println();
        System.out.printf("  SPN" +"\t\t" );for (float f:averageSPN) System.out.printf(" %6.2f \t\t\t", f);
        System.out.println();
        System.out.printf("  PP" +"\t\t" );for (float f:averagePP) System.out.printf(" %6.2f \t\t\t", f);
        System.out.println();
        System.out.printf("  PRR" +"\t\t" );for (float f:averagePRR) System.out.printf(" %6.2f \t\t\t", f);
        System.out.println();
    }
}