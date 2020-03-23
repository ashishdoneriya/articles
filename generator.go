package main

import (
	"bytes"
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"os"
)

var sourcePath string

func main() {
	sourcePath = "./template.html"
	generator("./")
}

func generator(parentDir string) {
	files, err := ioutil.ReadDir(parentDir)
	if err != nil {
		log.Fatal(err)
	}

	isFilesInitialied := false
	isDirsInitialized := false

	var dirArr string
	var filesArr string

	for _, file := range files {
		if file.IsDir() {
			generator(parentDir + file.Name() + "/")

			if isDirsInitialized {
				dirArr = dirArr + "," + "\"" + file.Name() + "\""
			} else {
				dirArr = "\"" + file.Name() + "\""
				isDirsInitialized = true
			}
		} else {
			if isFilesInitialied {
				filesArr = filesArr + "," + "\"" + file.Name() + "\""
			} else {
				filesArr = "\"" + file.Name() + "\""
				isFilesInitialied = true
			}
		}
	}
	os.Remove(parentDir + "index.html")
	copy(sourcePath, parentDir+"index.html")
	input1, err := ioutil.ReadFile(parentDir + "index.html")
	if err != nil {
		fmt.Println(err)
		os.Exit(1)
	}

	output1 := bytes.Replace(input1, []byte("DIRECTORIES_LIST_COMES_HERE"), []byte(dirArr), -1)

	if err = ioutil.WriteFile(parentDir+"index.html1", output1, 0666); err != nil {
		fmt.Println(err)
		os.Exit(1)
	}

	input2, err := ioutil.ReadFile(parentDir + "index.html1")
	if err != nil {
		fmt.Println(err)
		os.Exit(1)
	}

	output2 := bytes.Replace(input2, []byte("DOCUMENTS_LIST_COMES_HERE"), []byte(filesArr), -1)

	if err = ioutil.WriteFile(parentDir+"index.html2", output2, 0666); err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
	os.Remove(parentDir + "index.html")
	os.Remove(parentDir + "index.html1")
	os.Rename(parentDir+"index.html2", parentDir+"index.html")
}

func copy(src, dst string) error {
	in, err := os.Open(src)
	if err != nil {
		return err
	}
	defer in.Close()

	out, err := os.Create(dst)
	if err != nil {
		return err
	}
	defer out.Close()

	_, err = io.Copy(out, in)
	if err != nil {
		return err
	}
	return out.Close()
}
