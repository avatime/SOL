from typing import Optional
from pydantic import BaseModel


class Item(BaseModel):
    username: Optional[str]