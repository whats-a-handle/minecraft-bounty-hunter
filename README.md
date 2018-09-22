# minecraft-bounty-hunter 
## (Work in-progress)
A lightweight bounty-hunter plugin that relies on Bukkit/Spigot and The New Economy (TNE-Bukkit) plugin to add some excitement to modded servers.

## Functional Commands
* **Add bounty to another player (bountyhunter.player)**
`/addBounty <playername> <amount>`
* **Check if player has bounty (bountyhunter.player)** 
`/getBounty [playername]`
* **Set bounty of a player (bountyhunter.admin)**
`/setBounty <playername> <amount>`

## Claiming a Bounty
If a player with a bounty is killed by another player, the killer will receive the full bounty. The player who was killed will have their bounty amount set back to 0. In the future, a config option will allow the server owner to change the behavior of the payout. 

### Futue payout behavior options:
* Killer receives full bounty (**done**)
* Killer receives a percentage of the bounty, and the bounty is reduced by that percentage e.g. bounty of 100 will pay out 10% ($10) every time the player with the bounty is killed. After 10 kills, the players bounty will be $0. Percentage is configurable by server owner. (*future development*)
* Percentage of bounty is paid out each time the bountied player takes damage, eventually becoming 0. Percentage is configured by server owner (*future development*)


