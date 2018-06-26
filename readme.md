## **League of Legends Spectator App**

* How to run:

_As far as this application is running a lot of query in the background, I tried to reduce the amount of api calls as much as I could._
_So, I have saved the static datas into a local database. It contains informations about champions and runes._

* To do that, you need to run ChampionSave.xhtml and PerksSave.xhtml once after you have set up your local database to store these static datas.
You need to update the api keys, because I'm using developer api key that expires in 2 days.

You can submit an input parameter, validated 1-100 numbers. 
The application gives a win ratio for each player, which is calculated by the last winning games from the last X games of the specific player divided by X. 
Where X is the input parameter.

The application also gives a team win ratio which is calculated by the sum of team participants personal win ratio divided by the amount of participants / team.

**_EXAMPLE_**

input parameter = 10;
player has 7 win - 3 lose from the last 10 games
win ratio = (7/10)*100 = 70%

**Team Win ratio**

In case of a 5 v. 5 match:

team1 win ratio =
sum of team1 participants win ratio / 5

team2 win ratio =
sum of team2 participants win ratio / 5





