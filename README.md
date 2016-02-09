# BrianQuest2

TODO:
-Add icon.PNG and uncomment the section in Frame.java
-Some sort of "cast skill" player animation as a catch-all for skills (to use if the skill doesn't have a custom animation)
-Replace the old art with new art
-Make some music (or temporary music) and uncomment all the music/sound-playing code, make music play at the correct times
-Skill animations (do we want to up the frames and lower the milliseconds between frames?). Low priority since this will be the hardest thing
-Kev-Bot character - Brian's dead brother revived in a small robot
-Refactor the terrible temporary code in calculateDamage
-Work on formulas (specifically the ones involving probabilities and Dex)
-Duplicate the monster check for characters in draw() (if there's an exception when drawing from the character folder, try drawing with the NORMAL animation)
-Intro that plays after selecting a new game
-Monster AI - should they just randomly select a target? Or add some actual smartness? At least bosses should be somewhat smart
-Come up with good items/equipments, other active/passive skills, etc.
-Figure out which skills count as physical, add them to wakesUp() in Action.java
-Some way to make animationMap() code in Action.java less bad?
-Make Amp skill do something (should add the Amp status effect which enhances his next non-Amp skill)
-Make all the passive skills work (right now only the stat boosting ones do)
-Test AfterBattleAlert stuff at some point... it's 100% untested
-Save game slots - should have 3 (or more) slots that you save to, not just one save with New Game and Continue. Should also fix/improve the saving/loading logic with new games
-Dark/Holy elements? Would be good for evil things like Waffle House
-Death as the 8th status resistance? Reduces chance to die from instant-death spells. Could replace "murderable" property in Unit
-Cursor memory in battle
-Options menu - stuff like volume, cursor memory settings, etc.
-xOffset and yOffset for Shop events, similar to what Inn is doing
-Make critical hits more obvious somehow - write "Critical" above the damage in orange or something
-Handle negative resistances (weaknesses) - so -50% fire resistance would mean it takes 50% more damage. This might already be handled but I'm not sure
-Some way to handle ghosts being immune to physical damage - how do we determine if something is physical?
-Some sort of "super class" that holds anything we'd want to serialize (characters, inventory, map states, etc.)? Would make saving less terrible but would require a lot of restructuring
