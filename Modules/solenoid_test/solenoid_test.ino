int solenoidPin = 9;

void setup()
{
  pinMode(solenoidPin, OUTPUT);
}

void loop()
{
  long interval = 1000 * 30; // 30 seconds
  
  digitalWrite(solenoidPin, HIGH);
  delay(2000);
  digitalWrite(solenoidPin, LOW);
  delay(interval);
}
