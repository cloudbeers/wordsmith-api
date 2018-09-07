


# Notes

```
 helm install stable/chartmuseum --name chartmuseum --set env.open.DISABLE_API=false,env.open.ALLOW_OVERWRITE=true
 helm upgrade chartmuseum stable/chartmuseum --set env.open.DISABLE_API=false,env.open.ALLOW_OVERWRITE=true
 
```
