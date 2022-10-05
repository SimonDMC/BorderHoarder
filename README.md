# BorderHoarder

Collect unique items to expand the border!

## Starting a Game

- Start a game by running `/startbordergame`
- To start a game with a set seed, use `/startbordergame <seed>`
- To join the game after it has started, use `/joinbordergame`

## How to Play

### The Basics

- The world starts with a 1x1 border, expand the border by
  obtaining new items
- The border will expand by 1 block in each direction for each
  unique item you obtain
- To view all the items you have collected, use
  `/viewcollecteditems`
- To view all the items you are yet to collect, use
  `/viewmissingitems`
- As the game goes on, figuring out whether you already have an
  item or not gets more difficult, so you can use
  `/iscollected <item>` to check if an item has been collected

### Limitations

- The game (theoretically) ends when you collect every single
  obtainable items, however in practice that is impossible on
  most seeds, due to biome-specific items
- The nether works, and still has the border (which is synced
  with the overworld), but the end doesn't have a border, just
  so that obtaining an elytra and outer end island items is
  possible
- As of 1.19.2, there are 1,051 total obtainable items in
  survival, and collecting any other item (such as bedrock
  or command blocks) will not count towards the progression and
  will not expand the border.

### Competitive Mode

- The game is designed to be played in a cooperative manner,
  where players work together to get as many items as possible,
  however the game can also be played competitively.
- Pressing TAB will show the number of items collected by each
  player, so players can compete to see who can get the most
  items.

## Compatibility

- The plugin runs on Bukkit, meaning any fork of Bukkit should
  work, including Spigot and Paper
- The game only works on 1.19 and above, as it uses all the new
  items added in 1.19.
- The game can be played with any number of players, but it is
  recommended to play with 2-4 players. The more players there
  are, the easier the game will get.

## Feature Requests

If you have any feature requests, please open an issue on
GitHub.

## Download

The direct download link for the latest version is available
[here](https://github.com/SimonDMC/BorderHoarder/releases/latest/download/BorderHoarder.jar).