


class Game {

    char or
    int x,y, w,l

    public Game(String or, x,y, w,l){
        this.or = or
        this.x = x
        this.y= y
        this.w=w
        this.l=l
    }

    Set blocks = []

    String toString() { this.properties }
}


new File("levels/1/input.txt").readLines().forEach({ line ->

    def tokenize = line.tokenize()
    int id
    String rest
    (id, rest) = tokenize
    println "$id, $rest"
    println tokenize[1..5] as Game


})




