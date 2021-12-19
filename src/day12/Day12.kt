package day12

import readInput
import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        val nodes = mutableListOf<Node>()
        input.forEach { line ->
            val (node1Name, node2Name) = line.split("-")
            addEdge(nodes, node1Name, node2Name)
        }

        return countPaths(nodes)
    }

    fun part2(input: List<String>): Int {

        return 0
    }

    val input = readInput("day12/Day12")
    val test = readInput("day12/Day12_test")

    val partOneTest = part1(test)
    println("test part1: $partOneTest")
    check(partOneTest == 19)

//    val partOneInput = part1(input)
//    println("part1: $partOneInput")
//    check(partOneInput == 1617)
////
//    val partTwoTest = part2(test)
//    println("test part2: $partTwoTest")
//    check(partTwoTest == 195)
////
//    val partTwoInput = part2(input)
//    println("part2: $partTwoInput")
}

private class Node(val name: String) {
    val adjacencyList = mutableListOf<Node>()
    override fun toString(): String =
        System.lineSeparator().plus("Node: $name, neighbours${adjacencyList.map { it.name }}")

}

private fun addEdge(nodes: MutableList<Node>, node1Name: String, node2Name: String) {
    val node1 = nodes.find(node1Name) ?: Node(node1Name).also { node -> nodes.add(node) }
    val node2 = nodes.find(node2Name) ?: Node(node2Name).also { node -> nodes.add(node) }
    node1.adjacencyList.add(node2)
    node2.adjacencyList.add(node1)
}

private fun MutableList<Node>.find(nodeName: String): Node? {
    return this.find { node -> node.name == nodeName }
}

private fun countPaths(nodes: MutableList<Node>): Int {
    val sourceNode = nodes.find("start")!!
    val currentPath = mutableListOf<String>()
    val paths = mutableListOf<List<String>>()
    val visited = mutableSetOf<Node>()
    val stack = Stack<Node>()
    countPathsRecursive(sourceNode, currentPath, paths, visited, stack, "end")
    return paths.size
}

private fun countPathsRecursive(
    source: Node,
    path: MutableList<String>,
    paths: MutableList<List<String>>,
    visited: MutableSet<Node>,
    stack: Stack<Node>,
    endNodeName: String,
) {
    println("Moving to $source")
    path.add(source.name)
    if (source.name == endNodeName) {
        paths.add(path.toList())
        println("Path Found: $path")
        path.removeLast()
    } else {
        if (source.name.toCharArray()[0].isLowerCase()) {
            println("lower case room visited: ${source.name}")
            visited.add(source)
        }

        stack.addAll(source.adjacencyList.filter { it !in stack })
        println("stack is now: $stack")

        while (!stack.isEmpty()) {
            val neighbor = stack.pop()
            if (neighbor !in visited) {
                countPathsRecursive(neighbor, path, paths, visited, stack, endNodeName)
            }
        }
    }
}