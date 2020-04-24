package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var random = rand.New(rand.NewSource(time.Now().UnixNano()))

type Data struct {
	arrayList [][] int
	sync.WaitGroup
}


func GenereNewDataArr(arraySize int) []int {
	array := make([]int, arraySize)
	for i := 0; i < arraySize; i++ {
		array[i] = random.Intn(10)
	}
	return array
}

func changeArrayElements(list *Data, group *sync.WaitGroup, index, arraySize int) {
	elemToChange := random.Intn(arraySize)
	ToDo := random.Intn(2)
	switch ToDo {
	case 0:
		if list.arrayList[index][elemToChange] <= 15 {
			list.arrayList[index][elemToChange]++
		}
	case 1:
		if list.arrayList[index][elemToChange] >= 0 {
			list.arrayList[index][elemToChange]--
		}
	}
	group.Done()
}

func GenerNewData(n, arraySize int) *Data {
	return &Data{
		arrayList: Init(n, arraySize),
	}
}
func Init(n, arraySize int) [][]int{
	arrayList := make([][]int, n, arraySize)
	for i := 0; i < n; i++ {
		arrayList[i] = GenereNewDataArr(arraySize)
	}
	return arrayList
}


func Show(list *Data) {
	for _, i:= range list.arrayList {
		fmt.Println(i)
	}
	fmt.Println(" ")
}



func DoesNeedToStop(list *Data, n int) bool {
	sum := make([]int, n)
	for i := 0; i < n; i++ {
		counter := 0
		for _, j := range list.arrayList[i] {
			counter += j
		}
		sum[i] = counter
	}

	if IsAllEq(sum) {
		return true
	} else {
		return false
	}
}

func IsAllEq(array []int) bool{
	fmt.Println("The sum is: ", array)
	for i := range array {
		if array[0] != array[i] {
			return false
		}
	}

	return true
}

func ArraySimulator(list *Data, group *sync.WaitGroup, n, arraySize int) {
	stopFlag := false
	for !stopFlag {
		group.Add(n)
		for i:=0; i < n; i++ {
			go changeArrayElements(list, group, i, arraySize)
		}

		if DoesNeedToStop(list, n) {
			stopFlag = true
			fmt.Println("Stop")
		}

		Show(list)
		time.Sleep(1*time.Millisecond)
		group.Wait()
	}
}

func main() {
	const (
		N = 3
		SIZE = 5
	)

	arr := GenerNewData(3, 5)
	group :=new (sync.WaitGroup)
	ArraySimulator(arr, group, 3, 5)
}