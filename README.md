At first, we have to create a docker image for our app like below;

`docker build -t slartibartfast/demo-spring-boot-on-kubernetes .`

If you're using docker-desktop for your kubernetes test like me, then you should switch docker-desktop context;

`kubectl config use-context docker-desktop`

Before everything else you can check your nodes in your current contest with `kubectl get nodes` command, and you should see something like below;

    NAME             STATUS   ROLES                  AGE   VERSION
    docker-desktop   Ready    control-plane,master   18h   v1.22.5


Then, create a namespace, spring-boot-demo-attempt with `kubectl create namespace spring-boot-demo-attempt` command, and update the current kubectl context to use this namespace by default: `kubectl config set-context $(kubectl config current-context) --namespace=spring-boot-demo-attempt`

Create deployment with kubectl apply like `kubectl apply -f k8s/spring-boot-demo-attempt/deployment.yaml`

After wait for a while we should see something like below with `kubectl get all` command:

    NAME                                                             READY   STATUS    RESTARTS   AGE
    pod/demo-spring-boot-on-kubernetes-deployment-54b5cc56d8-78ghz   1/1     Running   0          6s
    pod/demo-spring-boot-on-kubernetes-deployment-54b5cc56d8-fxkpw   1/1     Running   0          6s
    pod/demo-spring-boot-on-kubernetes-deployment-54b5cc56d8-kg9hf   1/1     Running   0          6s
    
    NAME                                                        READY   UP-TO-DATE   AVAILABLE   AGE
    deployment.apps/demo-spring-boot-on-kubernetes-deployment   3/3     3            3           6s
    
    NAME                                                                   DESIRED   CURRENT   READY   AGE
    replicaset.apps/demo-spring-boot-on-kubernetes-deployment-54b5cc56d8   3         3         3       6s

After creating deployment object for our application, we should create a service object for external access to our application. For this we can execute
`kubectl apply -f k8s/spring-boot-demo-attempt/service.yaml` command.

To see what we got, run the `kubectl get svc` command. 

    NAME                                     TYPE       CLUSTER-IP    EXTERNAL-IP   PORT(S)          AGE
    demo-spring-boot-on-kubernetes-service   NodePort   10.98.54.86   <none>        9595:30095/TCP   6s

kubectl supports short names for many of the API objects as an alternative to their full name. For example, svc was used in the preceding command instead of the full name, service. Run the command `kubectl api-resources` to see all available short names.

One way to verify that the web server is also reachable internally in the cluster is to launch a small Pod that we can use to run curl from the inside. The curl command will use the internal cluster IP address and port. We don't need to use the internal IP address; instead, we can use a DNS name that is created for the Service in the internal DNS server. The short name of the DNS name is the same as the name of the Service, that is, _demo-spring-boot-on-kubernetes-service_.

Run the following command:

`kubectl run -i --rm --restart=Never curl-client --image=curlimages/curl --command -- curl -s 'http://demo-spring-boot-on-kubernetes-service:9595/payment'
`

And you should get something like below;

    If you don't see a command prompt, try pressing enter.
    [{"price":10,"currency":"TRY"}]pod "curl-client" deleted

This means that the web server is also accessible internally in the cluster!

This is basically all we need to know to be able to deploy our system landscape.

Wrap this up by removing the namespace containing our deployment:

`kubectl delete namespace spring-boot-demo-attempt`