# Mariam Abuelfotouh
# Birthday: 08/05/2001
#  email: m.abuelfotouh@innopolis.university

from random import randint
import os

FINAL_POS = 2014
MOVES_RANGE = 13
LOG_FILES_PATH = "./logs"
WIN_STRATEGY = []


# keeping logs in a file
def logging(mode, session_number, log):
    if not os.path.exists(LOG_FILES_PATH):
        os.mkdir(LOG_FILES_PATH)

    mode_name = ""
    match mode:
        case "1":
            mode_name = "smart"
        case "2":
            mode_name = "random"
        case "3":
            mode_name = "advisor"

    file_path = f"{LOG_FILES_PATH}/{mode_name}_{session_number}.txt"
    print(log)
    file = open(file_path, "w")

    file.write(f"Game mode is {mode_name}\n Session Number is {session_number}\n")
    for line in log:
        file.write(line + '\n')

    file.close()




# play mode
def advisor(current_position):
    steps = smart(current_position)
    print(f"Here is a hint! choose to move {steps} steps!")


# play mode
def smart(current_position):
    remaining_positions = FINAL_POS - current_position
    smart_move = 0
    if remaining_positions % (MOVES_RANGE + 1):
        smart_move = MOVES_RANGE

    else:
        smart_move = remaining_positions % (MOVES_RANGE + 1)

    smart_move = min(smart_move, remaining_positions)
    return smart_move


def random(current_position):
    step = FINAL_POS + 5
    while step + current_position > FINAL_POS:
        step = randint(1, MOVES_RANGE)
    return step


def play(session_number, mode, initial_position):
    calc_steps_func = None
    match mode:
        case "1":
            calc_steps_func = smart
        case "2":
            calc_steps_func = random
        case "3":
            calc_steps_func = smart

    # 0 for computer, 1 for player
    log = []
    current_turn = 0
    current_position = initial_position
    log.append(f"Starting position is {initial_position}\n")

    while current_position < FINAL_POS:
        steps = 0
        if current_turn:
            steps = calc_steps_func(current_position)
            event = f"Spoiler's turn !\n Spoiler's chose to take {steps} steps, from {current_position} to" \
                    f" {current_position + steps} "
            print(event)
            log.append(event)

        else:
            valid_responses = [str(x) for x in range(1, MOVES_RANGE + 1)]
            if mode == "3":
                advisor(current_position)

            steps = receive_reply(question=f"Your turn!\nCurrent position is {current_position}, how many steps do "
                                           f"you wanna take?", valid_responses=valid_responses)
            steps = int(steps)
            event = f"You chose to move {steps} steps, from {current_position} to {current_position + steps}"
            log.append(event)

        current_position += steps

        current_turn = not current_turn  # switching between 0 and 1 in each loop

    winner = "You"
    if current_turn:
        winner = "Spoiler"

    event = f"Winner is {winner}"
    print(event)
    log.append(event)

    # logging the game
    logging(mode=mode, session_number=session_number, log=log)


def start_play(session_number):
    mode = receive_reply(question="Kindly, enter the number of the mode u wanna play with?\n 1-Smart"
                                  "\n 2-Random \n 3-Advisor", valid_responses=["1", "2", "3"])
    print("Starting the session!")
    valid_responses = [str(x) for x in range(1, FINAL_POS)]
    valid_responses.append("-1")
    initial_pos = receive_reply(question="which position do u wanna start in? enter -1 to start at a random place",
                                valid_responses=valid_responses)
    initial_pos = int(initial_pos)
    if initial_pos == -1:
        initial_pos = randint(1, FINAL_POS - 1)
        print(f"You gonna start at position {initial_pos}")

    play(session_number=session_number, mode=mode, initial_position=initial_pos)


def receive_reply(question, valid_responses):
    while True:
        reply = input(question + '\n')
        if reply in valid_responses:
            return reply
        if reply == "exit":
            print("terminating the program")
            exit(0)
        print("Please enter a valid response")


def driver():
    session_number = 1
    while True:
        reply = receive_reply(question="Wanna start a session?(y/n)", valid_responses=["y", "n"])

        if reply == "n":
            print("Oh, That's sad :(!")
            break

        start_play(session_number)
        session_number += 1


if __name__ == '__main__':
    driver()
