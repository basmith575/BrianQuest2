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
<li><b>Data.java:</b> Stores the stuff that will be saved/serialized. Party, inventory, position, etc. The game will have 3 data files (for now) so you can have 3 save files.
<li><b>Frame.java:</b> Launches the game as an application. This is what you should run in Eclipse and also in the executable JAR file.
<li><b>Game.java:</b> All the game logic. Uses the patented "BrianQuest" logic of having everything divided up into states - map state, menu state, battle state, shop state, etc. The state determines what gets painted on the screen and what the buttons do. 99% of the game's logic is located here and almost none of it is documented or organized.
<li><b>Item.java:</b> Class representing an item. Specifies the name, type of item, and other properties. The inventory is an ArrayList of Items. A character's equipment is an array of 5 items (Weapon, Hat, Armor, Shoes, Accessory).
<li><b>Map.java:</b> The class of nightmares. Contains all the information about a Map, which is a 2-dimentional array of Tiles. Each Tile has a type (grass, water, dirt, etc.) and can have stuff like a Thing (tree, rock, house, etc.), Event (NPC, Shop, Inn, Chest, Save Point, Portal to another map, etc.), etc. In Game.java we draw the map by painting all the surrounding Tiles, then all the surrounding Things, then all the surrounding Events. <strike>Ideally, we'd want to create maps in some elegant way like having them be a separate file that we load in. However, we're still using the patented "BrianQuest" logic of just creating each Tile manually. It's about the same performance-wise but makes map-building kind of difficult.</strike> See MapEditor.java.
<li><b>MapEditor.java:</b> A "map editor" with limited functionality for now. Load a map file, add tiles and things, resize, etc. With this, will no longer have to create maps manually/dynamically/painfully in Map.java. Praise MapEditor.
<li><b>MapEditorFrame.java:</b> Run this to open the map editor.
<li><b>Monster.java:</b> Extends the parent Unit class, specific to a monster. Has the monster's stats, item drops, etc. Maps can have multiple ArrayLists of monsters.
<li><b>PassiveSkill.java:</b> A "passive" skill for a character (like Poison Resistance, Auto-Haste, HP+10%, etc.). Characters have an ArrayList of PassiveSkills. Need to keep track of current SP (for learning the skill from an equipment), whether or not the skill has been "learned" (SP has been maxed out or an equipment with that skill is equipped), and whether or not that skill is "equipped". When recalculating a character's stats, we loop through their equipped passive skills and apply their effects if any. For some passive skills we'll need to check/apply them elsewhere, like Alex's "Me Scared" which makes fleeing from battle always work.
<li><b>Unit.java:</b> Any "unit" that can exist in battle. Can be a character or monster. Some properties are specific to only characters or monsters, but many are shared like status effects, element resistances, stats, etc. Home of the extremely important recalculateStats(), which keeps a character's stats up-to-date with their level, equipment, and passive skills.
</ul>

<em><b>TODO:</b></em>

<b>High priority:</b>
<ul>
</ul>

<b>Medium priority:</b>
<ul>
<li>Come up with good items/equipments, other active/passive skills, etc.
<li>Options menu - stuff like volume/mute, cursor memory settings, button configuration, etc.
<li>Show skill %s in skill info menu (in yellow font or something)
<li>Replace "mp" collection in battleAction with "valueType" (HP or MP)
<li>Don't remove Amp status effect right away, remove it after displaying effects so it shows up during the effect
</ul>

<b>Low priority:</b>
<ul>
<li>Some sort of "cast skill" player animation as a catch-all for skills (to use if the skill doesn't have a custom animation). Should only bother doing this once the actual battle sprites are made (see next todo)
<li>How to handle player/monster attack animations? Just don't have them?
<li>Replace the old art with new art
<li>Skill animations. Probably the hardest thing. Could have "projectile" for things like shuriken rather than x/y offset.
<li>Start making the actual game
<li>Pass actual units into "targets" collection rather than their battle indices, remove horrible getUnitFromTargetIndex().
</ul>
