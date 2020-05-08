import pika
import threading
import json
import sys
from colorama import Fore, Style

EXCHANGE = 'space_agency'
USERNAME = 'guest'
PASSWORD = 'guest'
IP_ADDRESS = '127.0.0.1'

def showInputOptions(keys):
    result = "Please change an option:\n"
    result = result + "q <- for quit\n"
    for index, option in enumerate(keys):
        result += "{} <- for {} \n".format(index, option)
    return result

def receive_responses(name):
    credentials = pika.PlainCredentials(USERNAME, PASSWORD)
    with pika.BlockingConnection(pika.ConnectionParameters(IP_ADDRESS, credentials = credentials)) as connection:
        channel = connection.channel()
        channel.exchange_declare(exchange=EXCHANGE, exchange_type='topic')
        queue_name = 'task.response.' + name
        channel.queue_declare(queue_name, auto_delete=True)
        channel.queue_bind(exchange=EXCHANGE, queue=queue_name, routing_key=queue_name)
        channel.basic_consume(queue=queue_name, on_message_callback=callback_from_carrier)
        channel.start_consuming()

def callback_from_carrier(ch, method, properties, body):
    print(Fore.GREEN + "Received response: " + body.decode() + Style.RESET_ALL)
    ch.basic_ack(delivery_tag=method.delivery_tag)

def handle_admin_messages():
    credentials = pika.PlainCredentials(USERNAME, PASSWORD)
    with pika.BlockingConnection(pika.ConnectionParameters(IP_ADDRESS, credentials = credentials)) as connection:
        channel = connection.channel()
        result = channel.queue_declare('', exclusive=True)
        queue_name = result.method.queue
        channel.queue_bind(exchange=EXCHANGE, queue=queue_name, routing_key='admin.agency')
        channel.basic_consume(queue=queue_name, on_message_callback=callback_from_admin)
        channel.start_consuming()

def callback_from_admin(ch, method, properties, body):
    print(Fore.RED + body.decode() + Style.RESET_ALL)
    ch.basic_ack(delivery_tag=method.delivery_tag)

agency_name = input("Enter name of your agency ")
print(agency_name)

threads = []
threads.append(threading.Thread(target=receive_responses, args=[agency_name]))
threads.append(threading.Thread(target=handle_admin_messages))

for t in threads:
    t.daemon = True
    t.start()

routing_keys = ["people", "cargo", "satellite"]

credentials = pika.PlainCredentials(USERNAME, PASSWORD)
with pika.BlockingConnection(pika.ConnectionParameters(IP_ADDRESS, credentials = credentials)) as connection:
    channel = connection.channel()
    channel.exchange_declare(exchange=EXCHANGE, exchange_type='topic')

    while True:
        request = input(showInputOptions(routing_keys))
        if request == "q":
            break
        try:
            chosen_option = routing_keys[int(request)]
            id = int(input("Enter a request number\n"))
            message_dict = {}
            message_dict['type'] = 'REQUEST'
            message_dict['name'] = agency_name
            message_dict['serivice'] = chosen_option
            message_dict['id'] = id
            message_str = json.dumps(message_dict)
            channel.basic_publish(exchange = EXCHANGE, routing_key = "task.send." + chosen_option, body=message_str)
            print("Request sent")
        except:
            print("Didn't understand, please enter input again")

    sys.exit()
