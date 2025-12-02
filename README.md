# Day 2: Log DB with a Hash Index

A tiny Java key–value database that appends records to a log file and uses an in-memory hash index for fast lookups.

## How It Works

1. `set(key, value)`
    - Append `key,value` to the end of the file
    - Store the starting byte offset in the in-memory hash index

2. `get(key)`
    - Look up the offset in the hash index
    - Seek directly to that position in the file
    - Read the line and return the value
