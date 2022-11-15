package main

import (
	"context"
	"fmt"
	"github.com/thinhdanggroup/executor"
	proto "go-benchmark/proto"
	"google.golang.org/grpc"
	"io/ioutil"
	"net/http"
	"strings"
	"time"
)

const (
	GRPC_HOST = "localhost:9091"
	HTTP_HOST = "http://localhost:9089"
)

func main() {
	wp, err := executor.New(executor.DefaultConfig())
	checkErr(err)
	noReq := int64(100000)
	callGrpc(wp, GRPC_HOST, noReq)
	time.Sleep(2 * time.Second)
	callHttp(wp, HTTP_HOST, noReq)
}

func callGrpc(wp *executor.Executor, host string, noReq int64) {
	// Prepare
	request := &proto.PingRequest{}
	conn, err := grpc.Dial(host, grpc.WithInsecure())
	checkErr(err)
	defer conn.Close()

	client := proto.NewPingServiceClient(conn)

	// Init connection
	_, err = client.Ping(context.Background(), request)
	checkErr(err)

	// Run test
	startTs := makeTimestamp()
	for i := int64(0); i < noReq; i++ {
		wp.Publish(func() {
			_, err := client.Ping(context.Background(), request)
			if err != nil {
				fmt.Errorf("error request: %s", err)
			}
		})
	}

	wp.Wait()
	execTime := (makeTimestamp() - startTs)
	report("Grpc", noReq, execTime)
}

func callHttp(wp *executor.Executor, host string, noReq int64) {
	// Prepare
	url := HTTP_HOST + "/api/ping"
	method := "POST"

	// Run
	startTs := makeTimestamp()
	for i := int64(0); i < noReq; i++ {
		wp.Publish(postRequest, method, url)
	}
	wp.Wait()
	execTime := (makeTimestamp() - startTs)
	report("HTTP", noReq, execTime)
}

func postRequest(method string, url string) {
	payload := strings.NewReader("{\n  \"timestamp\": 20\n}")

	client := &http.Client{}
	req, err := http.NewRequest(method, url, payload)
	checkErr(err)
	req.Header.Add("Content-Type", "application/json")
	res, err := client.Do(req)
	checkErr(err)

	defer res.Body.Close()
	_, err = ioutil.ReadAll(res.Body)
	if err != nil {
		fmt.Errorf("Error request: %s", err)
	}
}

func report(testName string, total int64, execTime int64) {
	fmt.Println("======================================")
	fmt.Println("TestName: " + testName)
	fmt.Println("Benchmark result for ", total, " request in ", execTime, " Microseconds")
	fmt.Println("Throughput :", total*1000000/execTime, "req/s")
	fmt.Println("Latency :", execTime/total, "Microseconds")
	fmt.Println("======================================")
}

func makeTimestamp() int64 {
	return time.Now().UnixNano() / 1000
}

func checkErr(err error) {
	if err != nil {
		panic(err)
	}
}
