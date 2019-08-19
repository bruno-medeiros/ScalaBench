

kc version
kc cluster-info

kc get nodes

kc config view


# Contexts
kubectl config get-contexts
kubectl config use-context docker-for-desktop


kubectl run kubernetes-bootcamp --image=gcr.io/google-samples/kubernetes-bootcamp:v1 --port=8080


## Scale

kubectl scale deployments/kubernetes-bootcamp --replicas=4


### -----------

kubectl proxy
kubectl get pods -o go-template --template '{{range .items}}{{.metadata.name}}{{"\n"}}{{end}}'



#Dashboard

kubectl apply -f \
https://raw.githubusercontent.com/kubernetes/dashboard/master/src/deploy/alternative/kubernetes-dashboard.yaml

kubectl proxy

# And navigate to your Kubernetes Dashboard at: http://localhost:8001/api/v1/namespaces/kube-system/services/kubernetes-dashboard/proxy

# Dashboard - Smashing
kubectl run smashing --image=visibilityspots/smashing --port=3030