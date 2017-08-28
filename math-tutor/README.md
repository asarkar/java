
$ jenv shell 9
$ java -version
asarkar:math-tutor$ find tutor -name "*.java" -type f -exec javac -d out/tutor {} +
asarkar:math-tutor$ java -p out/tutor \
  -m org.abhijitsarkar.java.mathtutor/org.abhijitsarkar.java.mathtutor.MathTutor
asarkar:math-tutor$ find addition-provider -name "*.java" -type f \
  -exec javac -d out/addition-provider -p out/tutor {} +
asarkar:math-tutor$ java -p out/tutor:out/addition-provider \
  -m org.abhijitsarkar.java.mathtutor/org.abhijitsarkar.java.mathtutor.MathTutor
```
