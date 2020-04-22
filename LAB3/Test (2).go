package main

import (
	"fmt"
	"math/rand"
	"time"
)

/*
func t2(v *chan int) {
	<-time.After(time.Second * 1)
	*v <- 1
}

func t1() {
	t := new(chan int)
	*t = make(chan int)
	go t2(t)
	fmt.Println(<-*t)
}

const num = 1000

func abs(n int) int {
	if n < 0 {
		return -n
	} else {
		return n
	}
}

type Monah struct {
	power   int
	monastr int
}

func Test(monahs *[num]Monah, f int, s int, chanel chan int) {
	//fmt.Printf("%d %d    --\n", f, s)
	//<-time.After(time.Second * 1)
	//fmt.Printf("%d %d    ++\n", &first, &second)
	if f == s {
		chanel <- f
		return
	} /*else if abs(f-s) == 1 {
		if monahs[f].power > monahs[s].power {
			return f
		}
	}
	var tchan chan int
	tchan = make(chan int)

	go Test(monahs, f, f+(s-f)/2, tchan)
	go Test(monahs, f+(s-f)/2+1, s, tchan)

	var first int
	var second int

	first = <-tchan
	second = <-tchan

	if monahs[first].power > monahs[second].power {
		(chanel) <- first
	} else {
		(chanel) <- second
	}
}

func main() {
	//start := time.Now()
	rand.Seed(time.Now().UnixNano())
	//theMine := [5]string{"rock", "ore", "ore", "rock", "ore"}
	//foundOre := finder(theMine)
	//fmt.Println(foundOre)
	//	go finder1(theMine)
	//go finder2(theMine)
	//	<-time.After(time.Second * 5) //пока не обращайте внимания на этот код

	//num = 100

	var monahs [num]Monah
	for i := 0; i < num; i++ {
		monahs[i].power = rand.Intn(100000)
		monahs[i].monastr = rand.Intn(2)
	}
	//var tchan chan int
	//tchan = make(chan int)
	var tchan chan int
	tchan = make(chan int)
	go Test(&monahs, 0, num-1, tchan)
	var ans int = <-tchan
	fmt.Println(monahs[ans].power)
	//elapsed := time.Since(start)
	//log.Printf("Binomial took %s", elapsed)
}

/*
func hello(n int) int {
	if n <= 1 {
		return 1
	}
	return n * hello(n-1)
}

func finder(theMine [5]string) int {
	for i := 0; i < 5; i++ {
		if theMine[i] == "ore" {
			return i
		}
	}
	return -1
}
*/

type Smoke int

var Selected Smoke

var GiveTwoItemes chan bool

var Inf [2]Smoke

const (
	Paper   Smoke = 0
	Tobacco Smoke = 1
	Matches Smoke = 2
	None    Smoke = 3
)

func MrMatch() {
	for {
		if Selected == None && (Inf[0] == Paper && Inf[1] == Tobacco || Inf[0] == Tobacco && Inf[1] == Paper) {
			Selected = Matches
			//	<-time.After(time.Millisecond * 100)
			Inf[0] = None
			Inf[1] = None
			fmt.Println("Smoke MrMatch")
			GiveTwoItemes <- true
		}
	}
}

func MrTob() {
	for {
		if Selected == None && (Inf[0] == Paper && Inf[1] == Matches || Inf[0] == Matches && Inf[1] == Paper) {
			Selected = Tobacco
			//	<-time.After(time.Millisecond * 100)
			Inf[0] = None
			Inf[1] = None
			fmt.Println("Smoke MrTop")
			GiveTwoItemes <- true
		}
	}
}

func MrPap() {
	for {
		if Selected == None && (Inf[0] == Tobacco && Inf[1] == Matches || Inf[0] == Matches && Inf[1] == Tobacco) {
			Selected = Paper
			//	<-time.After(time.Millisecond * 100)
			Inf[0] = None
			Inf[1] = None
			fmt.Println("Smoke MrPap")
			GiveTwoItemes <- true
		}
	}
}

func GiveRandom() Smoke {
	val := rand.Intn(3)
	switch val {
	case 0:
		return Paper
	case 1:
		return Tobacco
	case 2:
		return Matches
	default:
		return None
	}
}

func Mediator() {
	for {
		<-GiveTwoItemes
		Inf[0] = GiveRandom()
		Inf[1] = GiveRandom()
		if Inf[1] == Inf[0] {
			Inf[1] = (Inf[0] + 1) % 3
		}
		fmt.Println("Created ", Inf[0], " ", Inf[1])
		Selected = None
		rand.Intn(2)
	}
}

func main() {
	rand.Seed(time.Now().UnixNano())
	Selected = None
	GiveTwoItemes = make(chan bool)
	go MrPap()
	go Mediator()
	go MrTob()
	go MrMatch()
	GiveTwoItemes <- true

	<-time.After(time.Second * 5)
}
