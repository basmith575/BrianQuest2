# BrianQuest2

TODO
<ul>
<li>Add icon.PNG and uncomment the section in Frame.java
<li>Some sort of "cast skill" player animation as a catch-all for skills (to use if the skill doesn't have a custom animation)
<li>Replace the old art with new art
<li>Make some music (or temporary music) and uncomment all the music/sound-playing code, make music play at the correct times
<li>Skill animations (do we want to up the frames and lower the milliseconds between frames?). Low priority since this will be the hardest thing
<li>Kev-Bot character - Brian's dead brother revived in a small robot
<li>Refactor the terrible temporary code in calculateDamage
<li>Work on formulas (specifically the ones involving probabilities and Dex)
<li>Duplicate the monster check for characters in draw() (if there's an exception when drawing from the character folder, try drawing with the NORMAL animation)
<li>Intro that plays after selecting a new game
<li>Monster AI - should they just randomly select a target? Or add some actual smartness? At least bosses should be somewhat smart
<li>Come up with good items/equipments, other active/passive skills, etc.
<li>Figure out which skills count as physical, add them to wakesUp() in Action.java
<li>Some way to make animationMap() code in Action.java less bad?
<li>Make Amp skill do something (should add the Amp status effect which enhances his next non-Amp skill)
<li>Make all the passive skills work (right now only the stat boosting ones do)
<li>Test AfterBattleAlert stuff at some point... it's 100% untested
<li>Save game slots - should have 3 (or more) slots that you save to, not just one save with New Game and Continue. Should also fix/improve the saving/loading logic with new games
<li>Dark/Holy elements? Would be good for evil things like Waffle House
<li>Death as the 8th status resistance? Reduces chance to die from instant-death spells. Could replace "murderable" property in Unit
<li>Cursor memory in battle
<li>Options menu - stuff like volume, cursor memory settings, etc.
<li>xOffset and yOffset for Shop events, similar to what Inn is doing
<li>Make critical hits more obvious somehow - write "Critical" above the damage in orange or something
<li>Handle negative resistances (weaknesses) - so -50% fire resistance would mean it takes 50% more damage. This might already be handled but I'm not sure
<li>Some way to handle ghosts being immune to physical damage - how do we determine if something is physical?
<li>Some sort of "super class" that holds anything we'd want to serialize (characters, inventory, map states, etc.)? Would make saving less terrible but would require a lot of restructuring
</ul>
