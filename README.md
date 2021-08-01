# XBoard
Clipboard 📋 on steroids ⚡!

Tl;DR
Basically you can Copy Paste across devices.

## Example Use case :
Let's assume Raj is giving an online exam, and the code editor has gone full screen. If he tries to switch windows the test will be invalidated. 
Kundra, one of his friend agreed to help him with the code. Using this tool Kundra can send code directly into Raj's clipboard remotely form his PC.


## Platforms and Apps

- [ ] Desktop (Windows,MacOs and other Linux)  
- [ ] Desktop Cli
- [ ] Android App

## Features
The set of features are divided across versions, on thier complexity( PoC = proof of concept, ie. the bare minimum to show its working ).

###  Alpha 1 
- [ ] Text support 
- [ ] PoC Android 
- [ ] PoC Server Cli 
- [ ] PoC protobuff support if possible 
### Alpha 2 
- [ ] Android UI improvements
- [ ] PoC Desktop, Text only 
- [ ] Cli improvements
### Beta 1
- [ ] Desktop UI improvements
- [ ] Android Notifications support.
- [ ] Image and File Copy support.
### Beta 2
- [ ] Support for TyperX


## Architecture 

![Architecture](https://user-images.githubusercontent.com/39855414/127747972-175420f6-53c7-44a3-8ccc-656d89641791.png)

## Communication Protocoles 

All the communications happen by exchanging proper formated messages,which can be routed over any web sockets backend.
Each device has a unique id, all valid message exchanges must have this id. 
Currently the plan is to use Discord acts as a backend for routing the messages. With 2 channels :- 

1. **#updates** : This channel contains frequent updates or automated actions from all the clients. This can be used for features like devices online, what’s in their clipboard, or when a new client joins up.
2. **#request** : This channel is used for for completing user actions, like copying into other devices clipboards or pulling data from them like taking screenshots. This is a 2 step process a client posts a request message(like send me a ss) with a target device id, the target device after completing the action return a response message.

Although json might seems to be a easy solution, but handling files and images would be a difficult task.( although base64 encoding can be used but discord's text message length is limited by 2k characters. With protobuff we can generate platform agnostic classes that can be use to transmit any kind of data including binary.
A PoC must be done in the Alpha 1 phase trying out Google's ProtoBuff. 

### Message
- **id** String: The origin device id  
- **dest** Strings : The destination device id, can be a -1 for the action to execute on all device.  
- **message.type** : The type of request like request.ss for taking screenshot, request.paste for pasting content on the device  
- **message.payload** : Any additional info required for the completion of the request or the body of the response.

#### Message Types 
- request.paste.text -> a request to paste the text content in the target devices
- request.paste.image -> a request to paste the image content 
- request.copy.ss -> a request to take a screenshot from target device and copy in current device clipboard.
- response.paste.text -> an acknowledgment message after the text is pasted in the target device.
- response.paste.image -> an acknowledgment message after the image is pasted.
- response.copy.ss -> This response contains the image form the target device.
- update.join -> The first message which is sent by the client after joining the system.
- update.client -> A message to show the contents of the current client in case of an update,for example when a update.join message is received, all the other active clients will post an update.client, or when the clipboard contents change in a client.

#### Message Payloads 
Each of the payload is specific to each of the Message Types 
- request.paste.text -> 
```json 
{
   "id" : 1223,
   "dest" : 3234,
   "message.type" : "request.paste.text",
   "message.payload" : {
    "text" : "My name is Raj, naam toh suna hi hoga!" 
   }
}
```
- response.paste.text -> 
```json 
{
   "id" : 3234,
   "dest" : 1223,
   "message.type" : "response.paste.text",
   "message.payload" : {
    "status" : "Successful"  
   }  
}
```
- update.join -> 
```json 
{
   "id" : 3234,
   "dest" : -1,
   "message.type" : "update.join",
   "message.payload" : {
    "id" : 3234,
    "device.type" : "Android 12",
    "name" : "Galaxy Fold 2",
    "clipbaord.contents" : ["https://youtu.be/XZw3xVOlmww", "Bhoi Cinematic universe"] 
   }  
}
```





