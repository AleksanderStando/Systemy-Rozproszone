import pika
import threading
import sys
import inspect
from colorama import Fore, Style


EXCHANGE = 'space_agency'
USERNAME = 'guest'
PASSWORD = 'guest'
IP_ADDRESS = '127.0.0.1'

def receive_communication():
    credentials = pika.PlainCredentials(USERNAME, PASSWORD)
    with pika.BlockingConnection(pika.ConnectionParameters(IP_ADDRESS, credentials = credentials)) as connection:
        channel = connection.channel()
        channel.exchange_declare(exchange=EXCHANGE, exchange_type='topic')
        result = channel.queue_declare('', exclusive=True)
        queue_name = result.method.queue
        # admin wants to read only messages connected to tasks (thread won't read messages send by admin)
        # so admin will be able to read messages sent to routing key 'task.send.people' and response with key like 'task.response.agency1'
        # but won't be albe to read messages with keys like 'admin.agency', since admin is sending these messages anyway
        channel.queue_bind(exchange=EXCHANGE, queue=queue_name, routing_key='task.*.*')
        channel.basic_consume(queue=queue_name, on_message_callback=callback)
        channel.start_consuming()

def callback(ch, method, properties, body):
    print(Fore.GREEN + body.decode() + Style.RESET_ALL)
    ch.basic_ack(delivery_tag=method.delivery_tag)

thread = threading.Thread(target=receive_communication)
thread.daemon = True
thread.start()

credentials = pika.PlainCredentials(USERNAME, PASSWORD)
with pika.BlockingConnection(pika.ConnectionParameters(IP_ADDRESS, credentials = credentials)) as connection:
    channel = connection.channel()
    channel.exchange_declare(exchange=EXCHANGE, exchange_type='topic')

    input_str = """Type a message
        carrier [message] <- send to carriers
        agency [message] <- send to agencies
        all [message] <- send to everyone
        (q to quit)"""

    RESPONSE_LIST = ["carrier", "agency"]
    while True:
        str = input(inspect.cleandoc(input_str) + "\n")
        if str == "q":
            break
        if str.split(" ")[0].lower() == "all":
            message = " ".join(str.split(" ")[1:])
            # sends a message to both carrier and agency
            for target in RESPONSE_LIST:
                channel.basic_publish(exchange = EXCHANGE, routing_key = 'admin.' + target, body=message)
        elif str.split(" ")[0].lower() in RESPONSE_LIST:
            target = str.split(" ")[0].lower()
            message = " ".join(str.split(" ")[1:])
            channel.basic_publish(exchange = EXCHANGE, routing_key = 'admin.' + target, body=message)
        else:
            print("Didn't understand the input")

    print("Exiting program")
    connection.close()
    sys.exit()
