## Description of the `messagebroker` protocol

The protocol for the system `messagebroker` is based on XML.
`messagebroker` exposes the following commands:

- `send` - send a message to the queue;
- `read` - read a message from the queue;
- `fromServer` - an  message  from messagebroker if don't exist messages in queue;

Example of the structure for a `messagebroker` message:

```xml
<xmlmsg>
   <type><Comand><type/>
   <Comand><"send|read|fromServer"><Comand/>
   <payload><"message"><payload/>
</xmlmsg>
```
`xmlmsg` is root of each message
`payload` is payload of sent/received message.

`command` is type of action to be executed by the `messagebroker` for the `command` message.
