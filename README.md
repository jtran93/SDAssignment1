# SDAssignment1
9/21 11:00

changed getGameStatus method, got rid of second for loop.


9/21 8:00

made placeRandomMines() private, added to constructor, created protected constructor to call when random mines are not needed, refactored randomMinePlaceMentPlaces10Mines test and created minesAreRandomlyPlaced test. removed mineExposed from checkGameInProgress as you need to expose all other non-mine cells before you win. Merged GameStatus methods. Edited tests to reflect the merge. Removed GameInProgress code, since if you didnt win and you didnt lose, its still in progress.
 

9/21 5:45

Removed mineIsExposed method, refactored tests and code. added mineExposed variable to replace mineIsExposed.

9/21 3:25

Removed placeMine method, refactored tests above randomMinePlacementPlaces10Mines()



