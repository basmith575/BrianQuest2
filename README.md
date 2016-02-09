# BrianQuest2

<em>How to probably configure your Eclipse:</em>
<ol>
<li>Install Eclipse
<li>File -> Import -> Existing projects into workspace -> select the BrianQuest 2 folder
<li>To run the game, select Frame.java and click the green play button
</ol>

If you want to run Java through the command prompt, you're on your own because I know nothing about that.

<em>Files:</em>
<ul>
<li><b>Action.java:</b> Wrapper class for any action that can happen in battle. Attack, poison damage, flee, using a skill, using an item, etc. Has stuff like target type (one enemy, all allies, etc.), animations to display, information about the skill, etc.
<li><b>ActiveSkill.java:</b> A skill that can be used in battle (like Brian Punch or Barf). Classes are prefixed with "Active" to prevent duplicate names (e.g. ActiveBrianPunch has a reference to the BrianPunch Action). Characters have an ArrayList of ActiveSkills. Need to keep track of current SP (for learning the skill from an equipment) and whether or not the skill has been "learned" (SP has been maxed out or an equipment with that skill is equipped). Only learned active skills are usable.
<li><b>AfterBattleAlert.java:</b> A microscopic class that really did not need its own file and should probably just be put into Game.java. Used for storing all the alerts that should be displayed after battle (Level up, skill learned, etc.) so we can stick them in an ArrayList.
<li><b>BattleAction.java:</b> Should probably also be in Game.java rather than being its own file. Used for storing all the information about a turn in battle so we can just do the calculations one time and use that stored information for displaying and applying the effects. Stores stuff like the user of the action, targets, values (damage, healing, etc.), whether there was a critical hit or a miss, etc.
<li><b>Character.java:</b> Extends the parent Unit class, specific to a playable character. Has character-specific things like name, class, stat formulas, etc. The party is an array of Characters.
<li><b>Frame.java:</b> Launches the game as an application. This is what you should run in Eclipse and also in the executable JAR file.
<li><b>Game.java:</b> All the game logic. Uses the patented "BrianQuest" logic of having everything divided up into states - map state, menu state, battle state, shop state, etc. The state determines what gets painted on the screen and what the buttons do. 99% of the game's logic is located here and almost none of it is documented or organized.
<li><b>Item.java:</b> Class representing an item. Specifies the name, type of item, and other properties. The inventory is an ArrayList of Items. A character's equipment is an array of 5 items (Weapon, Hat, Armor, Shoes, Accessory).
<li><b>Map.java:</b> The class of nightmares. Contains all the information about a Map, which is a 2-dimentional array of Tiles. Each Tile has a type (grass, water, dirt, etc.) and can have stuff like a Thing (tree, rock, house, etc.), Event (NPC, Shop, Inn, Chest, Save Point, Portal to another map, etc.), etc. In Game.java we draw the map by painting all the surrounding Tiles, then all the surrounding Things, then all the surrounding Events. Ideally, we'd want to create maps in some elegant way like having them be a separate file that we load in. However, we're still using the patented "BrianQuest" logic of just creating each Tile manually. It's about the same performance-wise but makes map-building kind of difficult.
<li><b>Monster.java:</b> Extends the parent Unit class, specific to a monster. Has the monster's stats, item drops, etc. Maps can have multiple ArrayLists of monsters.
<li><b>PassiveSkill.java:</b> A "passive" skill for a character (like Poison Resistance, Auto-Haste, HP+10%, etc.). Characters have an ArrayList of PassiveSkills. Need to keep track of current SP (for learning the skill from an equipment), whether or not the skill has been "learned" (SP has been maxed out or an equipment with that skill is equipped), and whether or not that skill is "equipped". When recalculating a character's stats, we loop through their equipped passive skills and apply their effects if any. For some passive skills we'll need to check/apply them elsewhere, like Alex's "Me Scared" which makes fleeing from battle always work.
<li><b>Unit.java:</b> Any "unit" that can exist in battle. Can be a character or monster. Some properties are specific to only characters or monsters, but many are shared like status effects, element resistances, stats, etc. Home of the extremely important recalculateStats(), which keeps a character's stats up-to-date with their level, equipment, and passive skills.
</ul>

<em>TODO:</em>
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
