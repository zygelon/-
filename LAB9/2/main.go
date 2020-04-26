package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	c := make(chan int, 2000)
	var A [2000 * 2000]int
	var B [2000 * 2000]int
	var C [2000 * 2000]int

	r := rand.New(rand.NewSource(99))
	for i := 0; i < 2000*2000; i++ {
		A[i] = r.Intn(10)
		B[i] = r.Intn(10)
		C[i] = 0
	}
	now := time.Now()
	for i := 0; i < 2000; i++ {
		go GiveMeTheAns(i, i+1, c, &A, &B, &C)
	}
	for i := 0; i < 2000; i++ {
		<-c
	}
	now2 := time.Now()
	fmt.Print("N = ", 2000, "; ExecTime = ", int(now2.Sub(now).Seconds()*1000), " ms")
}

func GiveMeTheAns(i, n int, c chan int, A, B, C *[2000 * 2000]int) {
	for ; i < n; i++ {
		for j := 0; j < 2000; j++ {
			sum := 0
			for k := 0; k < 2000; k++ {
				sum += (*A)[i*2000+k] * (*B)[k*2000+j]
			}
			(*C)[i*2000+j] = sum
		}
	}
	c <- 1
}