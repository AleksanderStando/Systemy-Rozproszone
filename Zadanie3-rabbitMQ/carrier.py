import pika
import threading
import time
import json
import sys
from colorama import Fore, Style

POSSIBLE_KEYS = ["people", "cargo", "satellite"]
EXCHANGE = 'space_agency'
USERNAME = 'guest'
PASSWORD = 'guest'
IP_ADDRESS = '127.0.0.1'

#time in takes carrier to process task
DELAY = 0

def show_input_options(keys):
    result = "Please change an option:\n"
    for index, option in enumerate(keys):
        result += "{} <- for {} \n".format(index, option)
    return result

def choose_a_key(keys):
    print("Choose a service")
    while True:
        service = input(show_input_options(keys))
        try:
            service = keys[int(service)]
            return service
        except:
            print("Something went wrong, please choose your service again")

def take_orders(key):
    print("I am a {} service".format(key))

    credentials = pika.PlainCredentials(USERNAME, PASSWORD)
    with pika.BlockingConnection(pika.ConnectionParameters(IP_ADDRESS, credentials = credentials)) as connection:
        channel = connection.channel()
        channel.exchange_declare(exchange=EXCHANGE, exchange_type='topic')
        # due to using key as queue there is only 1 queue for people, 1 for cargo, and 1 for satellites
        # no matter how many carriers are there, only 1 is going to receive a task
        channel.queue_declare(key, durable=True)
        channel.queue_bind(exchange=EXCHANGE, queue=key, routing_key='task.send.' + key)
        channel.basic_qos(prefetch_count=1)
        channel.basic_consume(queue=key, on_message_callback=callback_from_agency)
        channel.start_consuming()

def callback_from_agency(ch, method, properties, body):
    print(Fore.GREEN + "Msg received: {}".format(body.decode()) + Style.RESET_ALL)
    time.sleep(DELAY)
    msg_dict = json.loads(body.decode())
    id = msg_dict['id']
    agency_name = msg_dict['name']
    msg_dict['type'] = 'RESPONSE'
    msg_dict['name'] = CARRIER_NAME
    msg_str = json.dumps(msg_dict)
    print(Fore.GREEN + "Msg {} processed".format(str(id)) + Style.RESET_ALL)
    ch.basic_ack(delivery_tag=method.delivery_tag)
    ch.basic_publish(exchange = EXCHANGE, routing_key = "task.response." + agency_name, body=msg_str)

def handle_admin_messages():
    credentials = pika.PlainCredentials(USERNAME, PASSWORD)
    with pika.BlockingConnection(pika.ConnectionParameters(IP_ADDRESS, credentials = credentials)) as connection:
        channel = connection.channel()
        result = channel.queue_declare('', exclusive=True)
        queue_name = result.method.queue
        channel.queue_bind(exchange=EXCHANGE, queue=queue_name, routing_key='admin.carrier')
        channel.basic_consume(queue=queue_name, on_message_callback=callback_from_admin)
        channel.start_consuming()

def callback_from_admin(ch, method, properties, body):
    print(Fore.RED + body.decode() + Style.RESET_ALL)
    ch.basic_ack(delivery_tag=method.delivery_tag)

CARRIER_NAME = input("Enter name of your carrier company ")

service1 = choose_a_key(POSSIBLE_KEYS)

remaining_keys = POSSIBLE_KEYS.copy()
remaining_keys.remove(service1)

service2 = choose_a_key(remaining_keys)

threads = []
threads.append(threading.Thread(target=take_orders, args=[service1]))
threads.append(threading.Thread(target=take_orders, args=[service2]))
threads.append(threading.Thread(target=handle_admin_messages))
#By enabling daemon we make threads die when main thread exits
for t in threads:
    t.daemon = True
    t.start()

while True:
    str = input("Press q to quit\n")
    if str == 'q':
        break

sys.exit()
