import java.io.File


fun readCOW(path: String): List<String> {
    val file = File(path)
    val lines = file.readLines()
    val find = Regex("""\b[mMoO]{3}\b""").findAll(lines.joinToString(" ")).toList()
    return  find.map { it.value }//lines.joinToString(" ").split(" ")
}


fun getBlocks(source: List<String>): HashMap<Int, Int> {
    val stack = mutableListOf<Int>()
    val blocks = HashMap<Int, Int>()
    for((i, op) in source.withIndex()) {
        if (op == "MOO") {
            stack.add(i)
        }
        if (op == "moo") {
            blocks[i] = stack[stack.lastIndex]
            blocks[stack.removeAt(stack.lastIndex)] = i
        }
    }
    return blocks
}


fun eval(source: List<String>) {
    var  buffer = Array<Char>(200) {_ -> (0).toChar()}
    var ptr = 0
    var i = 0
    val blocks = getBlocks(source)
    while (i<source.size) {
        while(i<source.size){
            when(source[i]) {
                "moO" -> ptr += 1
                "mOo" -> ptr -= 1
                "MoO" -> buffer[ptr] = buffer[ptr] + 1
                "MOo" -> buffer[ptr] = buffer[ptr] - 1
                "Moo" -> {
                    if (buffer[ptr].toInt() > 0) print("${buffer[ptr]}")
                    else buffer[ptr] = readLine()!![0]
                }
                "OOM" -> print("${buffer[ptr].toInt()}")
                "oom" -> buffer[ptr] = readLine()!!.toInt().toChar()
                "OOO" -> buffer[ptr] = (0).toChar()
                "MOO" -> {
                    if (buffer[ptr] == (0).toChar()) {
                        i = blocks[i]!!
                    }
                }
                "moo" -> {
                    if (buffer[ptr] != (0).toChar()) {
                        i = blocks[i]!!
                    }
                }
            }
            i += 1
        }
    }
}


fun main(args: Array<String>) {
    val chars = readCOW("hello.cow")
    //println(getBlocks(chars))
    eval(chars)
}