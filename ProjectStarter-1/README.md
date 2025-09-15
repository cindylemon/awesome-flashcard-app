# My Personal Project
## Cindy's Super Awesome Flashcards

I've tried **alot** of flashcard apps this year, but I haven't been able to find one that I really like.
The ones that I've tried so far at least, all suck. None of them had the simplicity, aesthetic, and 
*free-ness* that I was looking for. I've decided to take matters into my own hands and make my own flashcard
application! I hope that my fellow students and I can use this over the overly complex or money-grabbing apps.

Planned implementations:
- photos within the flashcards
- different sets and subjects
- different themes (dark mode/light mode)
- I really want it to look nice

## User Stories
- As a user, I want to be able to make new question + answer flashcards
- As a user, I want to be able to add different cards into different sets or subjects (lists)
- As a user, I want to be able to view my different sets
- As a user, I also want to be able to view the different cards within the sets
- As a user, I want to be able to choose to save the current state of my flashcards.
- As a user, I want to be able to choose to load my previous state of flashcards from file.

# Instructions for End User
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by adding multiple sets to the application AND being able to add different cards to each set.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by going through each flashcard (flipping, next, previous) and being able to delete them.
- You can locate my visual component by saving or loading data from the json
- You can save the state of my application by clicking the save button
- You can reload the state of my application by clicking the load button


# Phase 4: Part 2
Event Log:
Fri Mar 28 13:47:16 PDT 2025
New set named Cindy Wu Facts created
Fri Mar 28 13:47:31 PDT 2025
New card: 'How old is she?'
          '18' created
Fri Mar 28 13:47:39 PDT 2025
New card: 'What colour is her backpack?'
          're' created
Fri Mar 28 13:47:42 PDT 2025
Card 'What colour is her backpack?'  answer shown
Fri Mar 28 13:47:47 PDT 2025
Card question 'What colour is her backpack?' Removed
Fri Mar 28 13:47:59 PDT 2025
New card: 'What colour is her backpack?'
          'Red' created
Fri Mar 28 13:48:02 PDT 2025
Card 'How old is she?'  answer shown
Fri Mar 28 13:48:11 PDT 2025
New set named Poop facts created
Fri Mar 28 13:48:14 PDT 2025
Set named Poop facts removed

# UML Diagram:
<img width="322" alt="flashcardsUML" src="https://github.students.cs.ubc.ca/CPSC210-2024W-T2/project-p1b4i/assets/30530/fbeb4f5e-4fb8-473b-832f-e1caa5dbd778">

Looking at my UML Diagram, I think that I could have implemented some design patterns to make it look less messy and more systematic (like the Observer pattern to clean up dependancies, event handling, and interclass interactions). I think though, that having both the console UI and GUI together is part of why it looks so complicated and jumbly.
Other than this, my FlashcardsGUI class is very long and has over 650 lines of code. It is definitely doing too much and violating the rule of one responsibility per class I definitely should have abstracted some of the methods and tasks out and made it into different methods, because almost everything is in that class. 
