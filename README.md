



# Notes

## Chartmuseum

```
export POD_NAME=$(kubectl get pods --namespace core -l "app=chartmuseum" -l "release=chartmuseum" -o jsonpath="{.items[0].metadata.name}")
kubectl port-forward $POD_NAME 8080:8080
```

## Preview environment

```
export POD_NAME=$(kubectl get pods --namespace preview -l "app=wordsmith-api-preview-wordsmith-api" -o jsonpath="{.items[0].metadata.name}")
kubectl port-forward $POD_NAME 8080:8080
```


# Problems

```
 helm install stable/chartmuseum --name chartmuseum --set env.open.DISABLE_API=false,env.open.ALLOW_OVERWRITE=true
 helm upgrade chartmuseum stable/chartmuseum --set env.open.DISABLE_API=false,env.open.ALLOW_OVERWRITE=true
 
```


Problem in helm upgrade

```
+ helm init --client-only
$HELM_HOME has been configured at /.helm.
Not installing Tiller due to 'client-only' flag having been set
Happy Helming!
+ helm repo add wordsmith http://chartmuseum-chartmuseum.core.svc.cluster.local:8080
"wordsmith" has been added to your repositories
+ helm repo update
Hang tight while we grab the latest from your chart repositories...
...Skip local chart repository
...Successfully got an update from the "wordsmith" chart repository
...Successfully got an update from the "stable" chart repository
Update Complete. ⎈ Happy Helming!⎈ 
+ helm upgrade wordsmith-api-staging wordsmith/wordsmith-api --version 1.0.0-SNAPSHOT --install --namespace staging
Error: pods is forbidden: User "system:serviceaccount:core:default" cannot list pods in the namespace "kube-system"
```

https://stackoverflow.com/questions/46672523/helm-list-cannot-list-configmaps-in-the-namespace-kube-system

```
$ kubectl create clusterrolebinding tiller-cluster-rule --clusterrole=cluster-admin --serviceaccount=kube-system:tiller
Error from server (AlreadyExists): clusterrolebindings.rbac.authorization.k8s.io "tiller-cluster-rule" already exists

$ kubectl patch deploy --namespace kube-system tiller-deploy -p '{"spec":{"template":{"spec":{"serviceAccount":"tiller"}}}}'

deployment.extensions/tiller-deploy not patched

$ helm init --service-account tiller --upgrade
  $HELM_HOME has been configured at /Users/cyrilleleclerc/.helm.
  
  Tiller (the Helm server-side component) has been upgraded to the current version.
  Happy Helming!

```



```
$ kubectl create clusterrolebinding --user system:serviceaccount:core:default kube-system-cluster-admin --clusterrole cluster-admin
clusterrolebinding.rbac.authorization.k8s.io/kube-system-cluster-admin created
```
