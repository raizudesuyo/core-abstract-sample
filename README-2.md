# Testing

To test go to `http://localhost:8080/swagger-ui/` sample dataset already provided (ephemeral).

Please test using **store id = 1**, additional stores haven't been added yet, there is no way to add more using the API.

When using the stock-controller, **Version used should be 3**, since the stock controller uses a different model for shoes, they're totally incompatible at this point (at the point of view of whoever uses the API), another implementation of the Shoe API is pretty much needed.

## Further Improvements

- Unit test the controller itself.
- Add authorization / authentication for stock controller (people shoudn't be able to just update the stock [shop inventory])
- Use Generics for Abstract / Core / Facade of cores.