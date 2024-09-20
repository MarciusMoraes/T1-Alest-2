import sys

# Read the tree from a file
# You can specify the filename here or read it from command-line arguments
filename = 'casoe150.txt'

try:
    with open(filename, 'r') as file:
        tree_lines = file.readlines()
except FileNotFoundError:
    print(f"File '{filename}' not found.")
    sys.exit(1)

# Remove the size information from the first line
size_line = tree_lines.pop(0).strip()
rows_cols = size_line.strip().split()
if len(rows_cols) != 2:
    print("Invalid size information in the first line.")
    sys.exit(1)
rows, cols = map(int, rows_cols)

# Remove any trailing newline characters and ensure consistent line lengths
tree_lines = [line.rstrip('\n') for line in tree_lines]

# Adjust the tree to have equal length lines
max_length = max(len(line) for line in tree_lines)
tree_lines = [line.ljust(max_length) for line in tree_lines]

# Build the graph
class Node:
    def __init__(self, position, symbol, value=None):
        self.position = position
        self.symbol = symbol
        self.value = value
        self.neighbors = []

nodes = {}
for y, line in enumerate(tree_lines):
    for x, char in enumerate(line):
        if char.strip():
            if char.isdigit():
                value = int(char)
                symbol = 'value'
            elif char in ['#', 'V', 'W', '/', '\\', '|']:
                symbol = char
                value = None
            else:
                continue  # Ignore other characters
            node = Node((x, y), symbol, value)
            nodes[(x, y)] = node

# Connect the nodes
for (x, y), node in nodes.items():
    # Possible directions: up-left, up, up-right
    directions = [(-1, -1), (0, -1), (1, -1)]
    for dx, dy in directions:
        nx, ny = x + dx, y + dy
        neighbor = nodes.get((nx, ny))
        if neighbor:
            node.neighbors.append(neighbor)

# Find the root nodes (nodes at the bottom with the highest y value)
max_y = max(y for x, y in nodes)
roots = [node for (x, y), node in nodes.items() if y == max_y]

# DFS to find the maximum path sum
def dfs(node, visited, current_sum):
    if node in visited:
        return ([], 0)
    visited.add(node)
    total = current_sum
    if node.value is not None:
        total += node.value
    if not node.neighbors:
        return ([node], total)
    max_path = []
    max_total = 0
    for neighbor in node.neighbors:
        path, path_total = dfs(neighbor, visited.copy(), total)
        if path_total > max_total:
            max_total = path_total
            max_path = [node] + path
    return (max_path, max_total)

max_total = 0
max_path = []
for root in roots:
    path, total = dfs(root, set(), 0)
    if total > max_total:
        max_total = total
        max_path = path

# Print the result
print(f"Maximum total: {max_total}")
print("Path:")
for node in reversed(max_path):
    x, y = node.position
    symbol = node.symbol if node.symbol != 'value' else node.value
    print(f"({x}, {y}) - {symbol}")