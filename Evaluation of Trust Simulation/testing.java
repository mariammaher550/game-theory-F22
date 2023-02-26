package gametheory.snowball.students2022;


import gametheory.snowball.Player;


import java.util.ArrayList;
import java.util.HashMap;

class CopyCat implements Player{
    /**
     * This agent starts with throwing snowballs to hotfield
     * and then mimics the opponent
     */

    //initialize our agent to cooperate
    boolean coop = true;


    public String getEmail()
    {
        return "m.abuelfotouh@innopolis.university";
    }

    public int shootToHotField(int opponentLastShotToYourField, int snowballNumber,
                               int minutesPassedAfterYourShot) {
        int myShot = 0;

        coop = (opponentLastShotToYourField == 0 ); // agent will coop only if the opponent didn't attack

        if (coop && minutesPassedAfterYourShot == 4)
            myShot = maxSnowballsPerMinute(minutesPassedAfterYourShot);

        return Math.min(myShot, snowballNumber);
    }

    @Override
    public void reset() {
        coop = true;

    }

    public int shootToOpponentField(int opponentLastShotToYourField, int snowballNumber,
                                    int minutesPassedAfterYourShot) {
        int myShot = 0;

        coop = (opponentLastShotToYourField == 0 ); // agent will coop only if the opponent didn't attack

        if(!coop && minutesPassedAfterYourShot == 4)
            myShot = maxSnowballsPerMinute(minutesPassedAfterYourShot);


        return Math.min(myShot, snowballNumber);

    }





}


class AllAttack implements Player{
    /**
     * This agent throw snowballs to opponent
     * field every 4 minutes
     */

    @Override
    public void reset() {}

    @Override
    public int shootToOpponentField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        int myShot = 0;

        if(minutesPassedAfterYourShot == 4)
            myShot = maxSnowballsPerMinute(minutesPassedAfterYourShot);
        return Math.min(myShot, snowballNumber);
    }

    @Override
    public int shootToHotField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        //since this agent plays aggressively
        return 0;
    }

    @Override
    public String getEmail() {

        return null;
    }
}



class Grudger implements Player{
    /**
     * This agent starts with throwing snowballs to hot field
     * but once the opponent throw to its field
     * it always throw to opponent field
     */

    //initialize our agent to cooperate
    boolean coop = true;

    public Grudger(){
        this.reset();
    }



    public String getEmail()
    {
        return "m.abuelfotouh@innopolis.university";
    }

    public int shootToHotField(int opponentLastShotToYourField, int snowballNumber,
                               int minutesPassedAfterYourShot) {
        int myShot = 0;

        // agent will coop only if opponent never attacked
        if (opponentLastShotToYourField > 0)
            coop = false;

        if (coop && minutesPassedAfterYourShot == 4)
            myShot = maxSnowballsPerMinute(minutesPassedAfterYourShot);

        return Math.min(myShot, snowballNumber);
    }

    @Override
    public void reset() {
        coop = true;

    }

    public int shootToOpponentField(int opponentLastShotToYourField, int snowballNumber,
                                    int minutesPassedAfterYourShot) {
        int myShot = 0;

        // agent will coop only if opponent never attacked
        if (opponentLastShotToYourField > 0)
            coop = false;

        if(!coop && minutesPassedAfterYourShot == 4)
            myShot = maxSnowballsPerMinute(minutesPassedAfterYourShot);


        return Math.min(myShot, snowballNumber);

    }

}

public class MariamAbuelfotouhTesting {

    /**
     * runs a match between two agents
     * @param agent1
     * @param agent2
     * @return list of remaining snowballs with each player,
     * where the first element is for agent1 and 2nd element for player2
     */
    static int[] runMatch(Player agent1, Player agent2){
       //initializing snowballs w/ each player
        int snowballsAgent1 = 100;
        int snowballsAgent2 = 100;

        int x = 0;

        //initializing time
        int mins = 0; // for the whole timeline
        int timeSinceLastShotAgent1 = 1;
        int timeSinceLastShotAgent2 = 1;

        //initializing last shot
        int agent1ShotToOpponentLast = 0;
        int agent2ShotToOpponentLast = 0;


        while (mins < 60)
        {
            System.out.println("After " + (mins) + " passed");

            System.out.println("Mins passed after agent1 shot " + timeSinceLastShotAgent1);

            //checking shots from agent1 to both fields
            int agent1ShotOpponent = agent1.shootToOpponentField(agent2ShotToOpponentLast, snowballsAgent1, timeSinceLastShotAgent1);
            int agent1ShotHF = agent1.shootToHotField(agent2ShotToOpponentLast, snowballsAgent1, timeSinceLastShotAgent1);


            //checking shots from agent1 to both fields
            System.out.println("Mins passed after agent2 shot " + timeSinceLastShotAgent2);
            int agent2ShotOpponent = agent2.shootToOpponentField(agent1ShotToOpponentLast, snowballsAgent2, timeSinceLastShotAgent2);
            int agent2ShotHF = agent2.shootToHotField(agent1ShotToOpponentLast, snowballsAgent2, timeSinceLastShotAgent2);

           System.out.println(agent1ShotHF + " " + agent1ShotOpponent + " " + agent2ShotHF + " " + agent2ShotOpponent);


            // updating values for agent 1
            if (agent1ShotHF == 0 && agent1ShotOpponent == 0)
            {
                timeSinceLastShotAgent1++;
            }
            if (agent1ShotHF != 0 && agent1ShotOpponent == 0)
            {
                agent1ShotToOpponentLast = 0;
                snowballsAgent1 -= agent1ShotHF;

                timeSinceLastShotAgent1 = 1;
                x += 1;

                System.out.println("Agent 1 shot to HF " + agent1ShotHF);
            }

            if (agent1ShotHF == 0 && agent1ShotOpponent != 0)
            {
                agent1ShotToOpponentLast = agent1ShotOpponent;
                snowballsAgent1 -= agent1ShotOpponent;
                snowballsAgent2 += agent1ShotOpponent;

                System.out.println("Agent 1 shot to opponent " + agent1ShotOpponent);
                System.out.println("Agent 1 last shot" + timeSinceLastShotAgent1);

                //reset time of last shot
                timeSinceLastShotAgent1 = 1;
            }

            // updating values for agent 2

            if (agent2ShotHF == 0 && agent2ShotOpponent == 0)
            {
                timeSinceLastShotAgent2++;
            }
            if (agent2ShotHF != 0 && agent2ShotOpponent == 0)
            {
                agent2ShotToOpponentLast = 0;
                snowballsAgent2 -= agent2ShotHF;

                timeSinceLastShotAgent2 = 1;

                System.out.println("Agent 2 shot to hot field " + agent2ShotHF);
            }

            if (agent2ShotHF == 0 && agent2ShotOpponent != 0)
            {
                agent2ShotToOpponentLast = agent2ShotOpponent;
                snowballsAgent2 -= agent2ShotOpponent;
                snowballsAgent1 += agent2ShotOpponent;

                //reset time of last shot
                timeSinceLastShotAgent2 = 1;

                System.out.println("agent 2 shot to opponent " + agent2ShotOpponent);
            }

            // increment the time
            mins++;

            //add 1 snowball to each player
            snowballsAgent1 += 1;
            snowballsAgent2 += 1;


            System.out.println("Snowballs w/ 1st agent " + snowballsAgent1);
            System.out.println("Snowballs w/ 2nd agent " + snowballsAgent2);

            System.out.println("--------------------------------");
            System.out.println("\n");

        }

       int[] scores  = {snowballsAgent1, snowballsAgent2};

        System.out.println(x);
       return scores;
    }


    /**
     * tournament simulation
     * @param grudgerPlusPopulation
     * @param grudgerPopulation
     * @param copyCatPopulation
     * @param allAttackPopulation
     */
    static void tournament(int grudgerPlusPopulation, int grudgerPopulation, int copyCatPopulation, int allAttackPopulation){



        ArrayList<Player> population = new ArrayList<Player>();
        int populationSize = grudgerPlusPopulation + grudgerPopulation + copyCatPopulation + allAttackPopulation;
        HashMap<Player, Integer> snowballsLeft = new HashMap<Player, Integer>();


        //adding grudgers to our population
        for(int i = 0; i < grudgerPopulation; i++)
        {
            Grudger agent = new Grudger();
            population.add(agent);
            snowballsLeft.put(agent, 0);


        }

        //adding grudgerPlus to our population
        for(int i = 0; i < grudgerPlusPopulation; i++)
        {
            MariamAbuelfotouhCode agent = new MariamAbuelfotouhCode();
            population.add(agent);
            snowballsLeft.put(agent, 0);
        }

        //adding copycats to our population
        for(int i = 0; i < copyCatPopulation; i++)
        {
            CopyCat agent = new CopyCat();
            population.add(agent);
            snowballsLeft.put(agent, 0);
        }

        //adding AllAttack to our population
        for(int i = 0; i < allAttackPopulation; i++)
        {
            AllAttack agent = new AllAttack();
            population.add(agent);
            snowballsLeft.put(agent, 0);
        }

        // run tournament

        for(int i = 0; i < populationSize - 1; i++)
        {
            for(int j = i + 1; j < populationSize; j++)
            {
                population.get(i).reset();
                population.get(j).reset();
                int[] scores = runMatch(population.get(i), population.get(j));
                snowballsLeft.put(population.get(i), snowballsLeft.get(population.get(i)) + scores[0]);
                snowballsLeft.put(population.get(j), snowballsLeft.get(population.get(j)) + scores[1]);

            }
        }

        // printing result of tournament
        snowballsLeft.forEach(
                (key, value)
                        -> System.out.println(key + " " + value));


    }

}
