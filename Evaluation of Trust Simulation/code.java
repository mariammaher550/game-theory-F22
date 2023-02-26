package gametheory.snowball.students2022;

import gametheory.snowball.Player;

class MariamAbuelfotouhCode implements Player {


    boolean coop; //initialize our agent to cooperate at first
    int minutesSinceStart; // to keep track of time

    public MariamAbuelfotouhCode() {
        this.reset();
    }


    public String getEmail() {
        return "m.abuelfotouh@innopolis.university";
    }

    public int shootToHotField(int opponentLastShotToYourField, int snowballNumber,
                               int minutesPassedAfterYourShot) {

        int myShot = 0;

        // agent will coop only if opponent never attacked
        if (opponentLastShotToYourField > 0)
            this.coop = false;

        // the agent always attack in last round regardless if it was coop or not
        if (this.coop && minutesPassedAfterYourShot == 4 && this.minutesSinceStart != 60)
            myShot = maxSnowballsPerMinute(minutesPassedAfterYourShot);


        return Math.min(myShot, snowballNumber);
    }

    @Override
    public void reset() {
        this.coop = true;
        this.minutesSinceStart = 0;

    }

    public int shootToOpponentField(int opponentLastShotToYourField, int snowballNumber,
                                    int minutesPassedAfterYourShot) {
        int myShot = 0;
        this.minutesSinceStart += 1; // increment our time


        // agent will coop only if opponent never attacked
        if (opponentLastShotToYourField > 0)
            this.coop = false;

        // agent always attack in last round
        if ((!this.coop && minutesPassedAfterYourShot == 4) || (this.minutesSinceStart == 60)) {
            myShot = maxSnowballsPerMinute(minutesPassedAfterYourShot);
        }

        return Math.min(myShot, snowballNumber);

    }


}
