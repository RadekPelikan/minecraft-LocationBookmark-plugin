# Minecraft Location Bookmark Plugin

The Minecraft Location Bookmark Plugin is a utility for Minecraft servers that allows players to quickly bookmark certain locations and return to these locations rapidly. It also allows players to delete recorded locations and view the list of bookmarked places.

## Features

- Quickly save specific coordinates by giving them a name.
- Return to saved locations rapidly.
- Delete a saved location.
- View a list of all saved locations.
- Uses SQLite database for persistence of saved locations.

## Commands

- `/sv <location name>`: Saves the player's current location with the specified name.
- `/goto <location name>`: Teleports the player to the previously saved location with the given name.
- `/rmloc <location name>`: Deletes the location saved with the specified name.
- `/loclist`: Displays a list of all previously saved locations.

## Installation

1. Clone this repository to your local machine.
2. Move the `.jar` file in the repo to the `plugins` folder of your Minecraft server.
3. Start your server. This will automatically create the necessary database and tables.

## Usage

1. To save a location in the game, use the `/sv <location name>` command.
2. To go to a saved location, use the `/goto <location name>` command.
3. To delete a location, use the `/rmloc <location name>` command.
4. To view a list of saved locations, use the `/loclist` command.

## Notes

- This plugin was created using Java and the Bukkit API.
- The plugin uses an SQLite database, and all data is stored in a file named `locationbookmark.db`.
- Player locations are saved with x, y, and z coordinates.

## License

The source code of this project is licensed under the [MIT License](LICENSE).

If you have any questions or issues, please create a GitHub issue.
