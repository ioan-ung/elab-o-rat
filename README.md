# eLab O'Rat

A 2D top-down puzzle game built in Java, developed as a project for the PAOO (Advanced Object-Oriented Programming) course.

## About the project

You control a lab mouse that has just been placed into a research facility to have its problem-solving skills tested. Starting in a simple training room, the mouse learns to interact with buttons, boxes, and gates to reach pieces of cheese. Once it proves itself, it's moved into a much larger labyrinth, this time racing against the clock, and finally into a maze area where a cat is on the loose, hunting the mouse down while it tries to collect the last rewards.

The project was built to practice core object-oriented design principles in a real, working application rather than in isolated exercises — in particular, structuring a codebase around recognizable design patterns (Singleton, Flyweight, Abstract Factory) while keeping the game itself simple, playable, and fun.

## Screenshots

**Main Menu**

<img width="944" height="780" alt="menu" src="https://github.com/user-attachments/assets/16899878-0e61-4a8b-ba2e-8d9588b8cfa4" />

**Level 1 — Tutorial**

<img width="1280" height="840" alt="lvl1" src="https://github.com/user-attachments/assets/e612c5be-7890-4963-9561-1ba1c51fb41b" />

**Level 2 — Labyrinth**

<img width="1280" height="840" alt="lvl2" src="https://github.com/user-attachments/assets/075f4d2c-1cb7-4ae2-8edc-ad2fe3c4d64d" />

**Level 3 — Maze**

<img width="1000" height="657" alt="lvl3" src="https://github.com/user-attachments/assets/1440a4ae-8143-4e0b-b568-b1ab9cb68a30" />

## Gameplay

The player controls a mouse from a top-down perspective, navigating rooms and corridors blocked by mechanical gates. Gates are opened by pressing buttons scattered around the level, some of which are timed, some triggered by weight (boxes pushed onto them), and some permanent. Progress to the next level is only unlocked once every reward in the current level has been either collected or lost.

- **Tutorial** — an open space that introduces the core mechanics: buttons, gates, boxes, and cheese collection.
- **Labyrinth** — a maze-like level with puzzle-blocked paths, now under time pressure.
- **Maze** — the mouse must stay alert and avoid a cat enemy that becomes more aggressive as the player progresses.

Rewards take the form of cheese, which can be collected by the player or lost if an NPC reaches them first. The final score is based on the number of rewards collected and is saved to a leaderboard together with the player's name.

## Controls

- **WASD / Arrow keys** — move the mouse / navigate menus
- **Enter** — confirm selection in menus
- **Esc** — open the pause menu / go back

## Design patterns

The project intentionally showcases three design patterns:

- **Singleton** — `GameManager` (tracks score and game state) and `AssetManager` (manages loaded resources) are both accessed through a single shared instance.
- **Flyweight** — `AssetManager` caches sprites so each image is loaded into memory once and reused everywhere it's needed, rather than being reloaded per object.
- **Abstract Factory** — an `AbstractLevelFactory` creates the full family of objects for a given level (map, enemies, interactive objects, cheese), with a concrete factory per level type.

## Tech stack

- **Language:** Java
- **Rendering:** AWT (`Canvas`, `Graphics2D`, `BufferStrategy`) + Swing (`JFrame`, dialogs)
- **Build tool:** Maven
- **Map editor:** Tiled Map Editor (TMX format)
- **Sprite art:** LibreSprite
- **Database:** MariaDB (save/load, leaderboard)

## Team

Built as a two-person university project:

- **Ungureanu Ioan** — player movement, collisions, level structure, leaderboard, design pattern implementation
- **Nistor Constantin** — main menu, camera, interactions, save/load system, database integration
