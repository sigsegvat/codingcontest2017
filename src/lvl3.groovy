



new File("levels/3/input.txt").readLines().forEach({ line ->

    def tokenize = line.tokenize()
    int id
    String rest
    (id, rest) = tokenize
    println "$id, $rest"
    println tokenize[1..5] as Game


})




